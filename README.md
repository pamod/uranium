## Uranium: AI-powered Q&A for Spreadsheets

**Welcome to Uranium!** This project helps you automate Q&A for your spreadsheets by leveraging AI. It fetches questions from a specified Google Sheet, processes answers using the Gemini AI engine, and populates the answers in another column.

**Benefits:**

* Save time on repetitive tasks like RFP responses and research analysis.
* Leverage AI for insightful answers to your questions.
* Simplify workflow by automating data extraction and response generation.

![Q&A Flow Diagram](https://github.com/pamod/uranium/blob/main/images/QNAFlowDiagram.jpeg)

**Getting Started:**

**1. Prerequisites:**

* A Google Cloud Platform (GCP) project with a service account.
* A Gemini AI application and API key.
* Java installed on your system.

**2. Configuration:**

* Edit `application.yml` located in the `/release/config` directory.
* Configure the following sections:

  **a. Google Sheet Integration:**

    ```yaml
    sources:
      google:
        enabled: "true"
        name: "<ApplicationName>"  # Replace with your application name
        credential: "<CredentialFileName>"  # Replace with service account credential file name
    ```

    - Create a service account in your GCP project and download the credential file.
    - Place the downloaded credential file in the `/release/config` directory.

  **b. Gemini AI Integration:**

    ```yaml
    processors:
      gemini:
        enabled: "true"
        url: "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"
        key: "<KEY>"  # Replace with your Gemini API key
        delay: 5000  # Delay in milliseconds between requests (optional)
    ```

    - Create a Gemini application and get your API key.
  
  **c. Start Application:**
  
    - Navigate to 'release' directory and execute the following
   ```
    sh ./start.sh
   ```

**3. Sending a Request:**

Use a tool like Postman or curl to send a JSON request to `http://<host>:8080/qna/sheet url`. Replace `<host>` with your server's address. The request body should look like this:

```json
{
  "sheetName": "<STRING, NAME_OF_SHEET>",
  "questionColumn": "<CHAR, COLUMN LABEL OF QUESTION>",
  "answerColumn": "<CHAR, COLUMN LABEL OF ANSWER>",
  "startColumnIndex": <INT, START INDEX OF COLUMN TO READ>,
  "endColumnIndex": <INT, END INDEX OF COLUMN TO READ>,
  "suffix": "<STRING, LITERAL TO SUFFIX QUESTION (optional)>",
  "url": "<STRING, URL OF SHEET>"
}
```

**Example Request:**

```json
{
  "sheetName": "Monitor & Manage",
  "questionColumn": "C",
  "answerColumn": "D",
  "startColumnIndex": 24,
  "endColumnIndex": 67,
  "suffix": ",explain from WSO2 point of view",
  "url": "https://docs.google.com/spreadsheets/d/1eRJqk2P6b1hqu1hoQFKfQ6KCi5uyR5KHAbHdTMX8EU0/edit#gid=1420065883"
}
```

**4. Response:**

The Spring Boot service console will display messages indicating progress:

```
Gathering questions from spreadsheet..
Questions collected, now processing answers
Processing completed, please check the spreadsheet
```

**5. Check the Spreadsheet:**

Open your Google Sheet and check the designated answer column for the AI-generated responses.

Also check this video https://drive.google.com/file/d/1HnCUxGtkQMBaTehveooTpJ9Pd2HrjDn5/view?usp=drive_link 

**Important Notes:**

* If the processing engine encounters issues, "ERROR" will be written in the answer cell.
* Currently, only Google Sheets are supported.
* Ensure the Google Sheet has appropriate access permissions for the service account.

**Further Development:**

* Support for additional spreadsheet formats like XLS.
* Error handling and logging improvements.
* User interface for configuration and request submission.

**We hope Uranium empowers you to streamline your data analysis and research tasks!**

**Feel free to contribute to this project and suggest improvements.**


