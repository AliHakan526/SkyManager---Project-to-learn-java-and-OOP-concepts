# SkyManager - Flight Management System

**SkyManager** is a comprehensive Flight Management System developed in Java using Swing for the Graphical User Interface (GUI). This project is designed to demonstrate Object-Oriented Programming (OOP) concepts and provide a functional simulation of airline operations.

## Features

*   **User & Admin Panels**: Distinct interfaces for standard users and administrators.
*   **Flight Search**: Search for flights by route (Departure/Arrival) and date.
*   **Ticket Reservation**: Complete booking flow including seat selection and price calculation.
*   **Admin Management**: Tools for adding/removing flights and managing the fleet (planes).
*   **Flight Simulation**: A simulation mode to visualize flight statuses.
*   **Data Persistence**: Uses file-based storage for persistence (text files).

## Folder Structure

The project follows a standard structure:

*   `src`: Contains all Java source code.
    *   `main/java`: Application logic and GUI components.
    *   `test/java`: Unit tests.
*   `data`: Contains data files (e.g., `Planes.txt`, `Flights.txt`) used for storage.
*   `lib`: External dependencies (if any).
*   `bin`: Compiled output files.

## Getting Started

### Prerequisites

*   **Java Development Kit (JDK)**: Ensure you have Java installed (Java 8 or higher recommended).
*   **VS Code**: Recommended IDE (with Extension Pack for Java).

### Running the Application

1.  Clone the repository:
    ```bash
    git clone https://github.com/selimilica/SkyManager.git
    ```
2.  Open the project folder in VS Code.
3.  Navigate to `src/main/java/App.java`.
4.  Run the `main` method in `App.java` to start the application.

## Usage

*   **Login**: Start by logging in. (Check `data` files for default credentials if applicable, or create a new user).
*   **Search**: Enter your desired route and date to find flights.
*   **Admin**: Access the admin panel to manage the system backend.

## License

This project is for educational purposes.

##Acknowledgments

- Project Lead & Core Development: Ali Hakan (90% of implementation, architecture, and OOP design).

- QA & Debugging: Osman Selim Ilıca (Final debugging, unit testing, and code optimization).


