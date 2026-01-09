package main.java.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import main.java.FlightManagement.*;
import main.java.ReservationAndTicketing.*;
import main.java.ServicesAndManagers.*;

public class SimulationPanel extends JPanel {
    private MainFrame parent;
    private JPanel seatGrid; // Koltukların dizileceği panel (Dinamik olacak)
    private JButton[][] seatButtons; // Her uçağa göre boyutu değişecek
    private JLabel infoLabel;
    private JCheckBox syncCheckBox;
    private JScrollPane scrollPane;
    private Plane plane;

    public SimulationPanel(MainFrame parent) {

        this.parent = parent;
        setLayout(new BorderLayout(10, 10));

        // 1. ÜST PANEL (Geri Dönüş ve Bilgi)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backBtn = new JButton("<- Uçuş Listesine Dön");
        infoLabel = new JLabel("Simülasyon sayfası");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        topPanel.add(backBtn);
        topPanel.add(infoLabel);
        add(topPanel, BorderLayout.NORTH);

        // 2. ORTA PANEL (Boş bir seatGrid hazırlıyoruz)
        seatGrid = new JPanel(); 
        scrollPane = new JScrollPane(seatGrid); // Scroll her zaman hazır beklesin
        add(scrollPane, BorderLayout.CENTER);

        // 3. ALT PANEL (Simülasyon Kontrolleri)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        syncCheckBox = new JCheckBox("Thread Senkronizasyonu (Synchronized)");
        JButton startSimBtn = new JButton("Simülasyonu Başlat");
        startSimBtn.setBackground(new Color(220, 20, 60)); // Kırmızımsı buton
        startSimBtn.setForeground(Color.WHITE);

        controlPanel.add(syncCheckBox);
        controlPanel.add(startSimBtn);
        add(controlPanel, BorderLayout.SOUTH);

        // --- BUTON AKSİYONLARI ---
        backBtn.addActionListener(e -> parent.showPage("SEARCH"));

        startSimBtn.addActionListener(e -> {
            boolean isSync = syncCheckBox.isSelected();
            startSimulation(isSync);
        });
    }

    /**
     * FlightSearchPanel'den gelen Plane ID'ye göre uçağı bulur ve gridi çizer.
     */
    public void loadPlaneData(String planeID) {
    try {
        String result = main.java.InputOutput.Finder.Find("Planes.txt", planeID);
        if (result == null || result.trim().isEmpty()) {
            System.out.println("Uçak bulunamadı: " + planeID);
            return;
        }

        // "\\s+" kullanmak, aradaki 1'den fazla boşluk varsa hepsini tek seferde siler
        String[] planeAttributes = result.trim().split("\\s+");
        
        System.out.println("Okunan parça sayısı: " + planeAttributes.length); // DEBUG İÇİN

        // Güvenlik Kontrolü: Eğer 8 parça yoksa dur
        if (planeAttributes.length < 8) {
            System.out.println("HATA: Planes.txt dosyasındaki satır eksik! Satır: " + result);
            return;
        }

        this.plane = new Plane(
            planeID, 
            planeAttributes[1],
            Integer.parseInt(planeAttributes[2]),
            Integer.parseInt(planeAttributes[3]), // rows
            Integer.parseInt(planeAttributes[4]), // cols
            Integer.parseInt(planeAttributes[5]),
            Integer.parseInt(planeAttributes[6]),
            Integer.parseInt(planeAttributes[7])
        );

        int foundRows = plane.getRows();
        int foundCols = plane.getCols();

        infoLabel.setText("Uçak: " + planeID + " | Düzen: " + foundRows + "x" + foundCols);
        createSeatGrid(foundRows, foundCols);

    } catch (Exception e) {
        System.err.println("LoadPlaneData Hatası: " + e.getMessage());
        e.printStackTrace();
    }
}

    /**
     * Koltuk butonlarını silip yeni satır/sütun sayısına göre yeniden oluşturur.
     */
    private void createSeatGrid(int rows, int cols) {
        // 1. Önceki butonları ve tasarımı temizle
        seatGrid.removeAll();
        
        // 2. Yeni Grid tasarımı ata
        seatGrid.setLayout(new GridLayout(rows, cols, 3, 3));
        seatButtons = new JButton[rows][cols];

        // 3. Döngüyle yeni butonları ekle
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String seatName = (i + 1) + "" + (char) ('A' + j);
                JButton btn = new JButton(seatName);
                btn.setContentAreaFilled(false);
                btn.setOpaque(true);
                btn.setBackground(Color.GREEN);
                btn.setFont(new Font("Arial", Font.PLAIN, 9));
                btn.setMargin(new Insets(2, 2, 2, 2));
                seatButtons[i][j] = btn;
                seatGrid.add(btn);
            }
        }

        // 4. Panel hiyerarşisini güncelle (Görünmesi için şart)
        seatGrid.revalidate();
        seatGrid.repaint();
    }

    private void startSimulation(boolean isSync) {
        if (seatButtons == null) {
            JOptionPane.showMessageDialog(this, "Önce bir uçuş seçmelisiniz!");
            return;
        }
        ArrayList<Passenger> passengers = new ArrayList<>();
        for(int i=0;i<90;i++){
            Passenger passenger = new Passenger("123", "hako","unal",  "dude@gmail.com");   
            passengers.add(passenger);
        }
        JOptionPane.showMessageDialog(this, "Simülasyon Başlıyor... (Sync: " + isSync + ")");
        ReservationManager.startSimulation(passengers, plane, isSync,seatButtons);
        JOptionPane.showMessageDialog(this, "Simülasyon Tamamlandı! total price: " + CalculatePrice.calculatePrice(Passenger.getChosenSeats(), plane.getEcoRows(), plane.getEcoPrice(), plane.getBusPrice()));
    }
}