package main.java.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import main.java.InputOutput.Reader;
import main.java.InputOutput.Appender;

public class ReservationPanel extends JPanel {
    private int ecoRows;
    private int ecoPrice;
    private int busPrice;
    private MainFrame parent;
    private String currentFlightID;
    private String currentPlaneID;
    private JPanel seatGrid;
    private List<int[]> selectedSeatCoords; // [row, col] şeklinde koordinat tutar
    private Set<String> takenSeats; // "row-col" formatında dolu koltukları tutar
    private JLabel infoLabel;
    private String userID;

    public ReservationPanel(MainFrame parent) {
        this.parent = parent;
        this.selectedSeatCoords = new ArrayList<>();
        this.takenSeats = new HashSet<>();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Üst Kısım
        JPanel topPanel = new JPanel(new BorderLayout());
        infoLabel = new JLabel("Lütfen bir uçuş seçin", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JButton backBtn = new JButton("<- Geri Dön");
        topPanel.add(backBtn, BorderLayout.WEST);
        topPanel.add(infoLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 2. Orta Kısım
        seatGrid = new JPanel();
        JScrollPane scrollPane = new JScrollPane(seatGrid);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Alt Kısım
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton reserveBtn = new JButton("Rezervasyonu Tamamla");
        reserveBtn.setPreferredSize(new Dimension(200, 40));
        reserveBtn.setBackground(new Color(70, 130, 180));
        reserveBtn.setForeground(Color.WHITE);
        bottomPanel.add(reserveBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> parent.showPage("SEARCH"));

        reserveBtn.addActionListener(e -> {
            if (selectedSeatCoords.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen en az bir koltuk seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            confirmReservation();
        });
    }

    public void setUserID(String userID) { this.userID = userID; }

    public void loadReservationData(String flightID, String planeID) {
        this.currentFlightID = flightID;
        this.currentPlaneID = planeID;
        this.selectedSeatCoords.clear();
        this.takenSeats.clear();
        infoLabel.setText("Uçuş: " + flightID + " | Uçak: " + planeID);

        // Önce Reservation.txt'den dolu koltukları oku
        loadTakenSeats(flightID);

        try {
            String planesContent = Reader.Read("Planes.txt");
            String[] lines = planesContent.split("\\r?\\n");
            int rows = 0, cols = 0;

            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts[0].equals(planeID)) {
                    rows = Integer.parseInt(parts[3]); 
                    cols = Integer.parseInt(parts[4]); 
                    ecoRows = Integer.parseInt(parts[5]);
                    ecoPrice = Integer.parseInt(parts[6]);
                    busPrice = Integer.parseInt(parts[7]);
                    break;
                }
            }
            createSeatGrid(rows, cols);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Veriler yüklenemedi.");
        }
    }

    private void loadTakenSeats(String flightID) {
        try {
            String content = Reader.Read("Reservation.txt");
            if (content == null || content.isEmpty()) return;

            String[] lines = content.split("\\r?\\n");
            for (String line : lines) {
                String[] parts = line.split(" ");
                // Format: userID flightID planeID row col
                if (parts.length >= 5 && parts[1].equals(flightID)) {
                    // row ve col bilgisini "row-col" olarak kaydediyoruz
                    takenSeats.add(parts[3] + "-" + parts[4]);
                }
            }
        } catch (Exception e) {
            System.out.println("Dolu koltuklar okunurken hata: " + e.getMessage());
        }
    }

    private void createSeatGrid(int rows, int cols) {
        seatGrid.removeAll();
        seatGrid.setLayout(new GridLayout(rows, cols, 5, 5));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final int r = i;
                final int c = j;
                String seatLabel = (i + 1) + "" + (char) ('A' + j);
                JButton seatBtn = new JButton(seatLabel);
                
                // Dolu koltuk kontrolü
                if (takenSeats.contains(r + "-" + c)) {
                    seatBtn.setBackground(Color.RED);
                    seatBtn.setEnabled(false); // Tıklanamaz
                } else {
                    seatBtn.setBackground(Color.GREEN);
                    seatBtn.addActionListener(e -> {
                        if (seatBtn.getBackground() == Color.GREEN) {
                            seatBtn.setBackground(Color.YELLOW); // Seçim rengi (Sarı)
                            selectedSeatCoords.add(new int[]{r, c});
                        } else {
                            seatBtn.setBackground(Color.GREEN);
                            selectedSeatCoords.removeIf(coord -> coord[0] == r && coord[1] == c);
                        }
                    });
                }
                
                seatBtn.setOpaque(true);
                seatBtn.setBorderPainted(true);
                seatGrid.add(seatBtn);
            }
        }
        seatGrid.revalidate();
        seatGrid.repaint();
    }

    private void confirmReservation() {
        try {
            int totalPrice = 0;
            for (int[] coord : selectedSeatCoords) {
                // Dosyaya yaz: userID flightID planeID row col
                String record = userID + " " + currentFlightID + " " + currentPlaneID + " " + coord[0] + " " + coord[1];
                Appender.Append("Reservation.txt", record);

                // Fiyat hesapla (Her koltuk için ayrı hesaplanıp toplanır)
                totalPrice += main.java.ServicesAndManagers.CalculatePrice.calculatePrice(coord[0], ecoRows, ecoPrice, busPrice);
            }

            JOptionPane.showMessageDialog(this, "Rezervasyon başarıyla kaydedildi!\nToplam Fiyat: " + totalPrice + " TL");
            parent.showPage("SEARCH");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage());
        }
    }
}