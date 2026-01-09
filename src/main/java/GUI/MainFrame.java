package main.java.GUI;

import javax.swing.*;

import main.java.FlightManagement.Flight;
import main.java.FlightManagement.Plane;
import main.java.InputOutput.Appender;

import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    // Kart düzenini yönetmek için gerekli değişkenler
    private CardLayout cardLayout;
    private JPanel mainContainer;
    SimulationPanel simulationPage;
    FlightSearchPanel searchPage;
    ReservationPanel reservationPage;
    LoginPanel loginPage;
    static ArrayList<Flight>  flights;
    static ArrayList<Plane>  planes = new ArrayList<>();

    public MainFrame() { 

        flights = main.java.InputOutput.AutoFlightDelete.deletion();
        for(Flight flight : flights){
            String flightAttributes = flight.getFlightNum() + " " + flight.getPlaneID() + " " + flight.getDeparturePlace() + " " +
                    flight.getArrivalPlace() + " " + flight.getDate() + " " + flight.getHour() + " " + flight.getDuration();  
            Appender.Append("Flights.txt", flightAttributes);
        }
        String planeData = main.java.InputOutput.Reader.Read("Planes.txt");

if (planeData != null && !planeData.isEmpty()) {
    for (String line : planeData.split("\\r?\\n")) {
        if (line.trim().isEmpty()) continue;

        String[] attributes = line.trim().split("\\s+");

        if (attributes.length != 8) {
            System.out.println("Hatalı satır: " + line);
            continue;
        }
            Plane plane = null;
        try {
            plane = new Plane(
                attributes[0],
                attributes[1],
                Integer.parseInt(attributes[2]),
                Integer.parseInt(attributes[3]),
                Integer.parseInt(attributes[4]),
                Integer.parseInt(attributes[5]),
                Integer.parseInt(attributes[6]),
                Integer.parseInt(attributes[7])
            );
            planes.add(plane);
        } catch (Exception e) {
            System.out.println("Sayısal veri hatası: " + line);
        }
    }
}


        // 1. Pencere Temel Ayarları
        setTitle("SkyManager - Uçuş Yönetim Sistemi");
        setSize(1000, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        java.net.URL url = getClass().getResource("icon.jpg");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            this.setIconImage(icon.getImage());
        }
        
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        searchPage = new FlightSearchPanel(this);
        loginPage = new LoginPanel(this);
        simulationPage = new SimulationPanel(this);
        reservationPage = new ReservationPanel(this);
        AdminPanel adminPage = new AdminPanel(this, flights, planes);

        
        mainContainer.add(searchPage, "SEARCH");
        mainContainer.add(loginPage, "LOGIN");
        mainContainer.add(simulationPage, "SIMULATION");
        mainContainer.add(adminPage, "ADMIN");
        mainContainer.add(reservationPage, "RESERVATION");

        // 5. Ana Taşıyıcıyı Pencereye Ekle
        add(mainContainer);

        // İlk görünecek sayfayı seç
        showPage("LOGIN");

        setVisible(true);
    }

    /**
     * Diğer panellerden bu metodu çağırarak sayfa değiştirebilirsin.
     * Örn: frame.showPage("SIMULATION");
     */
    public void showPage(String pageName) {
        cardLayout.show(mainContainer, pageName);
    }

    // Uygulamayı başlatmak için Main metodu
    public static void main(String[] args) {
        // GUI thread'inde güvenli şekilde başlatmak için
        SwingUtilities.invokeLater(() -> new MainFrame());
    }

    public SimulationPanel getSimulationPanel() {
            return this.simulationPage; 
    }   

    public ReservationPanel getReservationPanel() {
        return this.reservationPage; 
    }


    public FlightSearchPanel getFlightSearchPanel() {
        return this.searchPage; 
    }
}