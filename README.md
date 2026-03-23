Cinema Ticketing System
A full-stack web application that allows users to browse movies, select specific seats in a cinema room, and purchase tickets. The system features role-based access, offering different flows for standard Users and Administrators.

📋 Table of Contents
Overview

Features

Tech Stack

Application Flow

Implementation Details

📖 Overview
This project is an interactive movie theater booking platform. Users can browse currently showing movies, select a cinema room, and pick their preferred seats using a visual seating chart. The application manages seat occupancy, shopping carts, and user ticket histories.

✨ Features
Standard User
Authentication: Sign up and log in.

Browse: View all available movies and cinema rooms.

Seat Selection: View visual layouts of rooms to see available and occupied seats.

Cart Management: Add selected seats to a personal cart before checking out.

Ticket History: View a log of all previously purchased tickets.

Administrator
Movie Management: Add new movies to the roster or edit existing movie details.

Room Management: Create new cinema rooms or edit existing room configurations.

Ticket Management: View and edit ticket histories across the application.

💻 Tech Stack
Backend: Java, Spring Boot (Spring MVC)

Frontend: HTML, CSS, Thymeleaf (View templates)

Data Handling: Service-oriented architecture (movieService, roomService, ticketService, userService)

🔄 Application Flow
User Journey
Home/Login: The user lands on the homepage and logs in.

Selection: The user navigates to "All Movies" or "All Rooms".

Movie Detail: The user selects a movie to view its specific page.

Seat Booking: The user is presented with a room grid showing available seats for that movie.

Cart: Clicking an available seat generates a ticket and adds it to the user's cart.

Checkout: The user purchases the ticket from the cart, updating their Ticket History.

🛠 Implementation Details (Key Endpoints)
The backend routing is primarily handled by the ViewController. Below are the core mappings:

GET / (Homepage): Retrieves all movies from the database and displays them. If a user is authenticated, it binds their username to the view.

GET /AllMovies: Retrieves the complete list of movies via the movieService and renders the Movie/AllMovies view.

GET /MoviePage/{movieId}: Extracts the unique movie ID from the URL, fetches the specific movie details, and dynamically displays the Movie/MoviePage view.

GET /Rooms: Fetches a list of all cinema rooms from the roomService and renders the Room view.

GET /TicketBooking/{movieId}: The core booking page. It fetches the movie, the primary room's seating chart, and a list of currently occupied seat IDs. This data is passed to the view to dynamically render the seat selection grid.

POST /AddToCart: Accepts an optional movieId and seatId. It identifies the current user via the Principal object, creates a new ticket for that seat/movie, and redirects the user to their cart.

GET /Cart: Retrieves all tickets associated with the currently authenticated user's cart via the ticketService and displays them on the Cart view.