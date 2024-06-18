# Plumber Game Web Application

Plumber Game Web Application is a web-based game built with Spring Boot. The game challenges players to rotate pipes to connect a water flow from start to end. It features user authentication, score tracking, comments, and ratings. The application provides a user-friendly interface built with Thymeleaf, HTML, CSS, and JavaScript. Additionally, the game can be played in a Command Line Interface (CLI) version.

## Features

- Play the Plumber game in a web browser or CLI
- Post scores and see how you rank against other players
- Rate the game and see the average rating from all players
- Comment on the game and engage with the community
- User registration and login functionality

## Technologies Used

- Java
- Spring Boot
- Thymeleaf
- HTML
- TailwindCSS
- JavaScript
- JPA
- Maven
- PostgreSQL

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

- Java 17
- Maven
- PostgreSQL

### Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/plumber-game.git
   cd plumber-game
   ```
2. **Run the setup script:**
   The setup script will configure the database, build the application, and prompt you to choose whether to play the Web UI version or the CLI version.
   sh
   ```sh
   chmod +x setup.sh
   ./setup.sh
   ```
## Running the Application

The setup script will prompt you to choose between the Web UI version and the CLI version of the game.

### For Web UI Version:
1. The script will build and run the web application.
2. Open your web browser and navigate to [http://localhost:8080/](http://localhost:8080/) to start playing the game.

### For CLI Version:
1. The script will build the application, start the web server in the background, and then run the CLI version of the game in the terminal.

## Database Setup

The `setup.sh` script will prompt you to enter your database configuration. Ensure you have PostgreSQL installed and running on your machine. The default database details are:

- **URL:** `jdbc:postgresql://localhost:5432/postgres`
- **Username:** `postgres`
- **Password:** `postgres`

## Usage

After starting the server (for Web UI) or running the CLI application, you can start playing the game, posting scores, rating the game, and leaving comments.

## Plumber Game Rules

Plumber Game is a puzzle game where the objective is to rotate pipes to connect a water flow from the start to the end. The game board consists of various pipe segments that can be rotated to create a continuous path for the water to flow.

## Images

### Login Page:
![Opera Snapshot_2024-06-18_165439_localhost](https://github.com/Kashalotnot/Plumber-web-game/assets/161368344/c670ea8e-1ed4-4bc7-810a-0475ff52be0b)


### Gameplay Screen with hint used:
![Opera Snapshot_2024-06-18_165856_localhost](https://github.com/Kashalotnot/Plumber-web-game/assets/161368344/f9bdfa6f-2404-407c-933b-d969d4a23924)



### Gameplay Screen with no hint used:
![Opera Snapshot_2024-06-18_165726_localhost](https://github.com/Kashalotnot/Plumber-web-game/assets/161368344/27fde55f-22ed-4e04-9555-4b725b08a57f)


### Comments and Ratings:
![Opera Snapshot_2024-06-18_170141_localhost](https://github.com/Kashalotnot/Plumber-web-game/assets/161368344/62870f29-c44c-4773-a507-c660add6a42c)

![Opera Snapshot_2024-06-18_170029_localhost](https://github.com/Kashalotnot/Plumber-web-game/assets/161368344/be0c0aab-1c47-4186-9149-63788d0f7357)


### Phone view(430x932):

![Opera Snapshot_2024-06-18_170401_localhost](https://github.com/Kashalotnot/Plumber-web-game/assets/161368344/150c2181-fd9a-443f-a580-f0dc33a9841a)

