Cinema Booking System
A full-stack movie theater management and ticket booking application. This system allows users to browse current films, select specific seats in various cinema rooms, and manage their ticket purchases, while providing administrators with tools to manage the theater's inventory.

📽️ Project Overview
This application provides a seamless experience for moviegoers. It features a robust backend built with Spring Boot and a dynamic frontend that visualizes theater layouts, movie listings, and user carts.

Key Features
User Authentication: Secure Sign-up and Login functionality.

Dynamic Movie Listings: Browse movies with real-time updates from the database.

Interactive Seating Chart: Visual representation of cinema rooms where users can see available vs. occupied seats.

Shopping Cart: Add tickets to a cart before final purchase.

Ticket History: Track previous purchases and view digital tickets.

Admin Dashboard: Dedicated tools for adding/editing movies, managing cinema rooms, and modifying tickets.

📐 Design & Logic
Flowchart Navigation
The application supports two primary user journeys:

User Flow: Browse Movies/Rooms → Select Seat → Create Ticket → Cart → Ticket History.

Admin Flow: All User capabilities + Add/Edit Movie listings, Room configurations, and Ticket records.

UI/UX Design
The interface is designed for high-resolution displays (MacBook Air mockups) with a focus on a "dark mode" cinema aesthetic:

Home Page: Grid view of active movies in different rooms.

Seat Selection: Color-coded grid (Green for available, Red for occupied).

Management: Clean, list-based views for editing movie metadata (Title, Release Date, Description).

💻 Implementation Details
The backend follows the Model-View-Controller (MVC) pattern using Spring Boot.

Core Controllers
Homepage (/): Fetches the full movie list and checks for an active session to display personalized user greetings.

MoviePage (/MoviePage/{movieId}): Dynamically retrieves specific film details using path variables.

SeatSelection (/TicketBooking/{movieId}): * Fetches the primary room for the selected movie.

Identifies occupiedSeatIds to prevent double-booking.

Renders the interactive grid.

Cart Management (/AddToCart & /Cart): * POST mapping to create ticket entities and link them to the user's Principal profile.

GET mapping to display all items currently in the user's cart.

Technical Stack
Backend: Java, Spring Boot, Spring Data JPA

Security: Principal-based authentication

Frontend: HTML5, CSS3, Thymeleaf Templates

Database: Relational Database (SQL-based)