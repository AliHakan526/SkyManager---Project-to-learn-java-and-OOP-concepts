package main.java.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.FlightManagement.Flight;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class FlightSearchPanel extends JPanel {
    private String userID;  
    private MainFrame parent;
    private JTable flightTable;
    private DefaultTableModel tableModel;

    public FlightSearchPanel(MainFrame parent) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Üst Kısım: Başlık ve Yenile Butonu
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Mevcut Uçuşlar", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JButton refreshBtn = new JButton("Listeyi Yenile");
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(refreshBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 2. Orta Kısım: Uçuş Tablosu (JTable)
        // Tablo başlıkları
        String[] columns = {"Uçuş ID", "Uçak ID", "Kalkış","Varış", "Tarih","Saat", "Süre"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tablo hücreleri elle değiştirilemesin
            }
        };
        
        flightTable = new JTable(tableModel);
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Sadece bir satır seçilebilir
        JScrollPane scrollPane = new JScrollPane(flightTable);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Alt Kısım: Butonlar
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton selectBtn = new JButton("Simülasyona Git");
        JButton reservationButton = new JButton("Rezervasyon Yap");
        selectBtn.setPreferredSize(new Dimension(250, 40));
        selectBtn.setBackground(new Color(46, 139, 87));
        selectBtn.setForeground(Color.WHITE);

        reservationButton.setPreferredSize(new Dimension(150, 40));
        reservationButton.setBackground(new Color(70, 130, 180));   
        reservationButton.setForeground(Color.WHITE);
        bottomPanel.add(reservationButton);
        
        bottomPanel.add(selectBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- AKSİYONLAR ---

        // Yenile butonuna basınca (İleride FlightManager'dan veri çekecek)
        refreshBtn.addActionListener(e -> refreshTable());

        // Seç butonu: Seçili uçuşu al ve simülasyona git
        selectBtn.addActionListener(e -> {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow != -1) {
                // Burada seçilen uçuşun ID'sini alabilirsin
                String planeID = tableModel.getValueAt(selectedRow, 1).toString();
                SimulationPanel simPanel = parent.getSimulationPanel();
        
                // 2. Simülasyon paneline hangi uçağın seçildiğini söyle
                simPanel.loadPlaneData(planeID);
                JOptionPane.showMessageDialog(this, planeID + " numaralı uçak seçildi. Simülasyona gidiliyor...");
                
                // DİKKAT: Burada seçilen uçuşu SimulationPanel'e aktarman gerekecek.
                parent.showPage("SIMULATION");
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir uçuş seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });

        reservationButton.addActionListener(e -> {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow != -1) {
                // Burada seçilen uçuşun ID'sini alabilirsin
                String planeID = tableModel.getValueAt(selectedRow, 1).toString();
                String flightID = tableModel.getValueAt(selectedRow, 0).toString();
                ReservationPanel resPanel = parent.getReservationPanel();
                
                // 2. Simülasyon paneline hangi uçağın seçildiğini söyle
                resPanel.loadReservationData(flightID, planeID);
                resPanel.setUserID(userID);
                JOptionPane.showMessageDialog(this, planeID + " numaralı uçuş seçildi. Rezervasyon ekranına gidiliyor...");
                
                // DİKKAT: Burada seçilen uçuşu SimulationPanel'e aktarman gerekecek.
                parent.showPage("RESERVATION");
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir uçuş seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });
        addData();
    }

    private void addData() {
    try {
        String flightsString = main.java.InputOutput.Reader.Read("Flights.txt");
        
        // 1. Dosya okuma kontrolü
        if (flightsString == null || flightsString.trim().isEmpty()) {
            System.out.println("HATA: Dosya boş veya bulunamadı! Path: " + new java.io.File("Flights.txt").getAbsolutePath());
            return;
        }

        // 2. Güvenli satır bölme (Windows/Linux uyumlu)
        String[] flights = flightsString.split("\\r?\\n"); 

        for (String flight : flights) {
            if (flight.trim().isEmpty()) continue; // Boş satırları atla

            // 3. Verileri böl
            String[] flightAttributes = flight.split(" ");
            
            // 4. Sütun sayısı kontrolü (Senin tabloda 7 sütun var)
            if (flightAttributes.length >= 7) {
                tableModel.addRow(new Object[]{
                    flightAttributes[0], // Uçuş ID
                    flightAttributes[1], // Uçak ID
                    flightAttributes[2], // Kalkış
                    flightAttributes[3], // Varış
                    flightAttributes[4], // Tarih
                    flightAttributes[5], // Saat
                    flightAttributes[6]  // Süre
                });
            } else {
                System.out.println("Eksik veri içeren satır atlandı: " + flight);
            }
        }
    } catch (Exception e) {
        System.out.println("Beklenmedik bir hata oluştu: " + e.getMessage());
        e.printStackTrace();
    }
}
    public void refreshTable() {
        // Burada FlightManager.getFlights() metodunu çağırıp tabloyu temizleyip yeniden doldurmalısın
        tableModel.setRowCount(0); // Tabloyu temizle
        addData(); // Yeniden yükle
    }
    

    public void setUserID(String userID) {
        this.userID = userID;
    }
}