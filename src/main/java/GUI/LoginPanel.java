package main.java.GUI;

import javax.swing.*;

import main.java.FlightManagement.Flight;

import java.awt.*;

public class LoginPanel extends JPanel {
    private MainFrame parent; // Sayfa değiştirmek için MainFrame referansı
    private FlightSearchPanel flightSearchPanel;

    public LoginPanel(MainFrame parent) {
        this.parent = parent;
        this.flightSearchPanel = parent.getFlightSearchPanel();
        
        // 1. Panel Düzeni (Layout) - Bileşenleri merkeze dizmek için GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Boşluklar
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 2. Başlık
        JLabel titleLabel = new JLabel("SkyManager Sistem Girişi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // 3. Kullanıcı Adı
        gbc.gridwidth = 1;
        gbc.gridy = 1; add(new JLabel("Kullanıcı Adı:"), gbc);
        JTextField userField = new JTextField(15);
        gbc.gridx = 1; add(userField, gbc);

        // 4. Şifre
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Şifre:"), gbc);
        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1; add(passField, gbc);

        // 5. Giriş Butonu
        JButton loginBtn = new JButton("Giriş Yap");
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(loginBtn, gbc);

        // --- BUTON AKSİYONU ---
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            // Basit Doğrulama Mantığı
            // (Burayı ileride dosya okuma 'users.txt' ile bağlayabilirsin)
            if (user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "Personel Girişi Başarılı!");
                parent.showPage("ADMIN"); // Admin paneline yönlendir
            } 
            else if (user.equals("yolcu") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "Hoş geldiniz!");
                parent.showPage("SEARCH"); // Uçuş arama/simülasyon ekranına yönlendir
                String userID = main.java.InputOutput.Finder.Find("users.txt","yolcu");
                userID = userID != null ? userID.split(" ")[0] : "yolcu";
                flightSearchPanel.setUserID(userID);
                
            } 
            else {
                JOptionPane.showMessageDialog(this, "Hatalı kullanıcı adı veya şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}