package main.java.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import main.java.ServicesAndManagers.FlightManager;
import main.java.FlightManagement.Flight;
import main.java.FlightManagement.Plane;  

public class AdminPanel extends JPanel {
    private MainFrame parent;
    private JTable flightTable, planeTable;
    private DefaultTableModel flightModel, planeModel;
    private JProgressBar reportProgressBar;
    FlightManager flightManager = new FlightManager();
    ArrayList<Flight> flights;
    ArrayList<Plane> planes;
    public AdminPanel(MainFrame parent,ArrayList<Flight> flights,ArrayList<Plane> planes) {
        this.parent = parent;
        setLayout(new BorderLayout(10, 10));
        this.flights = flights;
        this.planes = planes;
        // 1. ÜST KISIM: Başlık ve Geri Butonu
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("<- Ana Menü");
        JLabel titleLabel = new JLabel("Sistem Yönetim Merkezi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(backBtn, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 2. ORTA KISIM: Sekmeli Yapı (Uçuşlar ve Uçaklar)
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Uçuş Yönetimi", createFlightTab());
        tabbedPane.addTab("Uçak Yönetimi", createPlaneTab());
        add(tabbedPane, BorderLayout.CENTER);

        // 3. ALT KISIM: Senaryo 2 (Asenkron Rapor Paneli)
        add(createReportPanel(), BorderLayout.SOUTH);

        // Aksiyonlar
        backBtn.addActionListener(e -> parent.showPage("LOGIN"));
        
        
        // İlk açılışta verileri yükle
        refreshTables();
    }

    // --- UÇUŞ SEKME TASARIMI ---
    private JPanel createFlightTab() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Uçak", "Kalkış", "Varış", "Tarih", "Saat", "Süre"};
        flightModel = new DefaultTableModel(columns, 0);
        flightTable = new JTable(flightModel);
        panel.add(new JScrollPane(flightTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Uçuş Ekle");
        JButton updateDateBtn = new JButton("Tarih Güncelle");
        JButton updateHourBtn = new JButton("Saat Güncelle");
        JButton deleteBtn = new JButton("Uçuşu Sil");

        // Senin metotlarını çağıran buton aksiyonları
        deleteBtn.addActionListener(e -> {
            String id = getSelectedId(flightTable);
            if(id==null) return;
            if (id != null) {
                flights = flightManager.deleteFlight(flights,id); // SENİN METODUN
                refreshTables(); // DOSYADAN YENİLE
            }
        });

     addBtn.addActionListener(e -> {
    // 1. Giriş alanlarını tanımlıyoruz
    JTextField fId = new JTextField();
    JTextField pId = new JTextField();
    JTextField dep = new JTextField();
    JTextField arr = new JTextField();
    JTextField dat = new JTextField();
    JTextField hor = new JTextField();
    JTextField dur = new JTextField();

    // 2. Form panelini oluşturuyoruz (İsmi 'inputPnl' yaptık, çakışmaz)
    JPanel inputPnl = new JPanel(new GridLayout(0, 2, 5, 5));
    inputPnl.add(new JLabel("Uçuş ID:"));    inputPnl.add(fId);
    inputPnl.add(new JLabel("Uçak ID:"));    inputPnl.add(pId);
    inputPnl.add(new JLabel("Kalkış:"));     inputPnl.add(dep);
    inputPnl.add(new JLabel("Varış:"));      inputPnl.add(arr);
    inputPnl.add(new JLabel("Tarih:"));      inputPnl.add(dat);
    inputPnl.add(new JLabel("Saat:"));       inputPnl.add(hor);
    inputPnl.add(new JLabel("Süre:"));       inputPnl.add(dur);

    // 3. Pencereyi gösteriyoruz (İsmi 'res' yaptık)
    int res = JOptionPane.showConfirmDialog(this, inputPnl, 
            "Uçuş Ekle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    // 4. Eğer 'Tamam'a basıldıysa
    if (res == JOptionPane.OK_OPTION) {
        // Backend metodunu çağır (AdminManager'daki metodunun ismine göre kontrol et)
        flightManager.createFlight(
            fId.getText(), pId.getText(), dep.getText(), 
            arr.getText(), dat.getText(), hor.getText(), dur.getText(),flights
        );
        
        // Tabloyu ve listeyi dosyadan yenile
        refreshTables();
    }
});


        updateDateBtn.addActionListener(e -> {
            String id = getSelectedId(flightTable);
            if(id==null) return;
            String newDate = JOptionPane.showInputDialog("Yeni Tarih (GG-AA-YYYY):");
            if (id != null && newDate != null) {
                flightManager.updateDate(newDate,id,flights); // SENİN METODUN
                refreshTables();
            }
        });

        updateHourBtn.addActionListener(e -> {
            String id = getSelectedId(flightTable);
            if(id==null) return;
            String newHour = JOptionPane.showInputDialog("Yeni Saat (SS:DD):");
            if (id != null && newHour != null) {
                flightManager.updateHour(newHour,id,flights); // SENİN METODUN
                refreshTables();
            }
        });

        btnPanel.add(addBtn); btnPanel.add(updateDateBtn); 
        btnPanel.add(updateHourBtn); btnPanel.add(deleteBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- UÇAK SEKME TASARIMI ---
    private JPanel createPlaneTab() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Model", "Kapasite", "Satır", "Sütun", "EcoSatır", "EcoFiyat", "BusFiyat"};
        planeModel = new DefaultTableModel(columns, 0);
        planeTable = new JTable(planeModel);
        panel.add(new JScrollPane(planeTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Uçak Ekle");
        JButton deleteBtn = new JButton("Uçağı Sil");

        deleteBtn.addActionListener(e -> {
            String id = getSelectedId(planeTable);
            if (id != null) {
                System.out.println("Seçilen Uçak ID: " + id); // DEBUG İÇİN
                planes = flightManager.deletePlane(id,planes); // SENİN METODUN
                refreshTables();
            }
        });

          addBtn.addActionListener(e -> {
    // 1. Giriş alanlarını tanımlıyoruz
    JTextField pId = new JTextField();
    JTextField pMo = new JTextField();
    JTextField cap = new JTextField();
    JTextField rows = new JTextField();
    JTextField cols = new JTextField();
    JTextField ecor = new JTextField();
    JTextField ecop = new JTextField();
    JTextField busp = new JTextField();

    // 2. Form panelini oluşturuyoruz (İsmi 'inputPnl' yaptık, çakışmaz)
    JPanel inputPnl = new JPanel(new GridLayout(0, 2, 5, 5));
    inputPnl.add(new JLabel("Uçak ID:"));    inputPnl.add(pId);
    inputPnl.add(new JLabel("Uçak modeli"));    inputPnl.add(pMo);
    inputPnl.add(new JLabel("kapasite:"));     inputPnl.add(cap);
    inputPnl.add(new JLabel("Koltuk sırası sayısı:"));      inputPnl.add(rows);
    inputPnl.add(new JLabel("yan yana koltuk:"));      inputPnl.add(cols);
    inputPnl.add(new JLabel("ekonomi/business sınırı:"));       inputPnl.add(ecor);
     inputPnl.add(new JLabel("Ekonomi Fiyat:")); inputPnl.add(ecop);
    inputPnl.add(new JLabel("Business Fiyat:"));       inputPnl.add(busp);

    // 3. Pencereyi gösteriyoruz (İsmi 'res' yaptık)
    int res = JOptionPane.showConfirmDialog(this, inputPnl, 
            "Uçak Ekle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    // 4. Eğer 'Tamam'a basıldıysa
    if (res == JOptionPane.OK_OPTION) {
        // Backend metodunu çağır (AdminManager'daki metodunun ismine göre kontrol et)
        flightManager.createPlane(
            pId.getText(), pMo.getText(), Integer.parseInt(cap.getText()), Integer.parseInt(rows.getText()),
            Integer.parseInt(cols.getText()), Integer.parseInt(ecor.getText()), Integer.parseInt(ecop.getText()),
            Integer.parseInt(busp.getText()),planes
        );
        
        // Tabloyu ve listeyi dosyadan yenile
        refreshTables();
    }
});

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- SENARYO 2: ASENKRON RAPOR PANELİ ---
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Sistem Analiz Raporu (Senaryo 2)"));

        JButton reportBtn = new JButton("Rapor Oluştur");
        reportProgressBar = new JProgressBar(0, 100);
        reportProgressBar.setStringPainted(true);
        reportProgressBar.setPreferredSize(new Dimension(300, 20));

        reportBtn.addActionListener(e -> startAsynchronousReport());

        panel.add(reportBtn);
        panel.add(reportProgressBar);
        return panel;
    }

    // --- YARDIMCI FONKSİYONLAR ---

    public void refreshTables() {
    flightModel.setRowCount(0);
    planeModel.setRowCount(0);

    for (Flight f : flights) {
        flightModel.addRow(new Object[]{
            f.getFlightNum(),
            f.getPlaneID(),
            f.getDeparturePlace(),
            f.getArrivalPlace(),
            f.getDate(),
            f.getHour(),
            f.getDuration()
        });
    }

    for (Plane p : planes) {
        planeModel.addRow(new Object[]{
            p.getPlaneID(),
            p.getPlaneModel(),
            p.getCapacity(),
            p.getRows(),
            p.getCols(),
            p.getEcoRows(),
            p.getEcoPrice(),
            p.getBusPrice()
        });
    }
}


    private String getSelectedId(JTable table) {
        int row = table.getSelectedRow();
        if (row != -1) return table.getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(this, "Lütfen bir satır seçin!");
        return null;
    }

    private void startAsynchronousReport() {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i += 5) {
                    Thread.sleep(150); // Ağır işlem simülasyonu
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                reportProgressBar.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(AdminPanel.this, "Analiz Tamamlandı! Rapor src/Report.txt dosyasına yazıldı.");
                reportProgressBar.setValue(0);
            }
        };
        worker.execute();
    }
}