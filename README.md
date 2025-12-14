# RAFTuristički vodič

A Vue.js single-page application for browsing and sharing travel inspiration. Visitors can explore curated tourist articles by destination or activity, while registered users can publish, edit, and manage their own content. Administrators and editors can also maintain destination data and user records.

## Key Features
- **Article discovery:** Browse the latest and most-read articles on the home page, or filter by destination and activity for focused trip ideas.
- **Rich article pages:** View detailed write-ups and including destination context.
- **Community comments:** Registered users can add feedback and questions to each article to keep guides up to date.
- **JWT-secured workspace:** Log in to unlock content creation, editing, and deletion for articles and destinations.
- **Destination directory:** Explore all destinations with or without authentication, with additional management tools available to signed-in staff.
- **Activity feeds:** Jump directly to articles tied to specific activities for quick inspiration.

## Technology Stack
- **Vue 2 + Vue Router:** SPA routing and view orchestration.
- **Bootstrap 5 & BootstrapVue:** Responsive layout, navigation, and form components.
- **Axios plugin:** Preconfigured HTTP client (`src/plugins/axios.js`) that targets the backend at `http://localhost:8081`, automatically attaching JWT tokens for authenticated calls.
- **Backend integration:** Designed to consume a REST API (Spring Boot service located in `../Projekat-RAFTuristickiVodic`) that issues JWTs for login and exposes `/api/...` endpoints for articles, destinations, and users.
- 
## Representative Backend Routes
The Vue frontend expects the Spring Boot service in `../Projekat-RAFTuristickiVodic` to expose these REST endpoints:

- **Auth & users:**
  - `POST /users/login` – Authenticate with email/password and receive a JWT.
  - `POST /users` – Register a new user (unique email enforced).
  - `PUT /users` – Update user profile fields; admins can adjust roles or status.
  - `POST /users/status/{id}` – Toggle a user’s active/blocked status.

- **Articles (`/clanak`):**
  - `GET /clanak/sve/{filter}?page=&limit=` – Paginated list filtered by “najnoviji”, “najcitaniji”, or destination/activity flags.
  - `GET /clanak/byid/{id}` – Fetch a single article for reading.
  - `POST /clanak` – Create a new article (requires JWT).
  - `PUT /clanak` – Update an existing article.
  - `DELETE /clanak/{id}` – Remove an article.
  - `POST /clanak/brposeta/{id}` – Increment the view counter after opening an article.
  - `GET /clanak/aktivnost/{id}?page=&limit=` – Paginated articles filtered by activity.

- **Comments (`/kom`):**
  - `GET /kom/{id}` – List comments for the given article ID.
  - `POST /kom/{id}` – Add a new comment to the article (registered users).

- **Destinations (`/dest`):**
  - `GET /dest/sve?page=&limit=` – Paginated destination directory.
  - `GET /dest/ids?ids=1,2,3&page=&limit=` – Fetch selected destinations by comma-separated IDs.
  - `GET /dest/ime/{id}` – Resolve a destination’s name.
  - `POST /dest` – Create a destination; `PUT /dest` to update; `DELETE /dest/{id}` to remove.

## Authentication Notes
- Successful login stores a JWT in `localStorage`; navigation guards ensure protected routes require a valid, non-expired token.
- User details (name, role) are decoded from the token to personalize the experience and gate authoring tools.

Enjoy building and sharing new travel stories!
