# Email Service

The **Email Service** is a microservice responsible for sending emails for notifications and communication within the QuantumGrid platform. It manages the delivery of emails such as user registration confirmations, password resets, notifications for new posts or comments, and other system-generated emails.

## Features

- Send transactional emails (e.g., registration, password reset)
- Template-based email generation
- Support for multiple email providers (e.g., SMTP, SendGrid)
- Scheduled email delivery
- Logging and monitoring of email delivery status

## Technology Stack

- **Java**: Programming language
- **Spring Boot**: Framework for building the microservice
- **Spring Mail**: For handling email operations
- **MongoDB**: Document-based database for storing email templates and logs

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven** for build automation
- **MongoDB** installed and running
- An SMTP server or email provider credentials (e.g., SendGrid, Mailgun)

### Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/bobnetnetwork/quantumgrid-email-service.git
    cd quantumgrid-email-service
    ```

2. **Configure the application properties:**

   Update the `src/main/resources/application.properties` file with your MongoDB and email provider settings:

    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/quantumgrid
    spring.mail.host=smtp.your-email-provider.com
    spring.mail.port=587
    spring.mail.username=your-email-username
    spring.mail.password=your-email-password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    ```

3. **Build the application:**

    ```bash
    mvn clean install
    ```

4. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

### API Endpoints

- `POST /api/emails/send` - Send an email using a predefined template
  - **Request Body:** JSON object containing recipient information, template ID, and any dynamic content to populate the template.
- `POST /api/emails/schedule` - Schedule an email to be sent at a later time
  - **Request Body:** JSON object with email details and scheduled time.
- `GET /api/emails/logs` - Retrieve logs of sent emails
  - **Query Parameters:** `startDate`, `endDate`, `recipientEmail` (optional filters)

### Email Templates

Email templates are stored in MongoDB and can be customized as needed. You can add, update, or delete templates using the following endpoints:

- `POST /api/templates` - Add a new email template
- `GET /api/templates/{id}` - Retrieve a specific email template
- `PUT /api/templates/{id}` - Update an existing email template
- `DELETE /api/templates/{id}` - Delete an email template

### Customization

- **Templates:** Customize email templates by adding HTML or text templates to MongoDB via API or direct database access.
- **Email Provider:** Switch email providers by updating the SMTP settings in the `application.properties` file.

### Error Handling

The service includes basic error handling for common email issues, such as invalid email addresses, failed deliveries, or provider connection issues. Errors are logged and can be monitored via a centralized logging system.

### Contributing

Please read the [CONTRIBUTING.md](https://github.com/bobnetnetwork/quantumgrid/blob/main/CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

### License

This project is licensed under the GPL-3.0 license - see the [LICENSE.md](https://github.com/bobnetnetwork/quantumgrid/blob/main/LICENSE.md) file for details.
