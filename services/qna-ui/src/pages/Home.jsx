import { Container } from 'react-bootstrap';
import homeimage from '../images/home.jpeg';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Modal from 'react-bootstrap/Modal'
import { useState } from 'react';
import axios from 'axios';


const Home = () => {
    const alphabet = Array.from({ length: 26 }, (_, i) => String.fromCharCode(i + 97));
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Extract form values
        const sheetUrl = event.target.elements.formGridURL.value;
        const suffix = event.target.elements.formGridSuffix.value || ""; // Optional field
        const tabName = event.target.elements.formGridTabName.value;
        const questionColumn = event.target.elements.formGridQuestionColumn.value;
        const answerColumn = event.target.elements.formGridAnswerColumn.value;
        const startIndex = parseInt(event.target.elements.formGridStartIndex.value);
        const endIndex = parseInt(event.target.elements.formGridEndIndex.value);

        const data = {
            "sheetName": tabName, // Assuming tabName is the sheet name
            "questionColumn": questionColumn,
            "answerColumn": answerColumn,
            "startColumnIndex": startIndex,
            "endColumnIndex": endIndex,
            "suffix": suffix,
            "url": sheetUrl,
        };

        try {
            const response = await axios.post('http://localhost:8080/qna/sheet', data, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log('Form submitted successfully:', response.data);
            handleShow();

        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    return (
        <div style={{
            backgroundImage: `url(${homeimage})`, backgroundPosition: "center",
            backgroundRepeat: "no-repeat",
            backgroundSize: "cover",
            height: "100vh",
            width: "100vw"
        }}>
            <Container className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
                <Row>
                    <Form style={{
                        height: "45vh",
                        width: "50vw",
                        backgroundColor: 'rgba(0, 0, 0, 0.5)',
                        color: 'white'
                    }} onSubmit={handleSubmit} >
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridURL">
                                <Form.Label>Sheet URL</Form.Label>
                                <Form.Control />
                            </Form.Group>
                        </Row>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridSuffix">
                                <Form.Label>Suffix</Form.Label>
                                <Form.Control />
                            </Form.Group>
                        </Row>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridTabName">
                                <Form.Label>Tab Name</Form.Label>
                                <Form.Control />
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridQuestionColumn">
                                <Form.Label>Question Column</Form.Label>
                                <Form.Select>
                                    <option value="">Select a letter</option>
                                    {alphabet.map((letter) => (
                                        <option key={letter} value={letter}>
                                            {letter.toUpperCase()}
                                        </option>
                                    ))}
                                </Form.Select>
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridAnswerColumn">
                                <Form.Label>Answer Column</Form.Label>
                                <Form.Select>
                                    <option value="">Select a letter</option>
                                    {alphabet.map((letter) => (
                                        <option key={letter} value={letter}>
                                            {letter.toUpperCase()}
                                        </option>
                                    ))}
                                </Form.Select>
                            </Form.Group>
                        </Row>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridStartIndex">
                                <Form.Label>Start Index</Form.Label>
                                <Form.Control type='number' />
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridEndIndex">
                                <Form.Label>End Index</Form.Label>
                                <Form.Control type='number' />
                            </Form.Group>
                        </Row>
                        <Row>
                            <Button variant="primary" style={{ backgroundColor: 'gray' }} type="submit">
                                Process
                            </Button>
                        </Row>
                    </Form>
                </Row>
            </Container>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Processing Started</Modal.Title>
                </Modal.Header>
                <Modal.Body>Please check the spreadsheet for answers</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </div >
    );
};

export default Home;