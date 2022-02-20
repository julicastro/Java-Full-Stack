import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Card from "react-bootstrap/Card";
import { useState } from "react";
import { loginUser } from "../services/userService";
import Alert from "react-bootstrap/Alert"

const Login = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [sendingData, setSendingData] = useState(false);
    // el error es cualquier tipo de dato y se inicializa como objeto vacio

    const login = async (e: React.SyntheticEvent) => {
        // usamos prevent default ya q se le aplica el evente a un form
        e.preventDefault();
        try {
            setSendingData(true);
            setError("") // asi se limpia el alert 
            const res = await loginUser(email, password) // en el resultado viene el token para logearnos
            console.log(res.data.token);
            setSendingData(false);
        } catch (errors: any) {
            // setError(errors.response.data.errors);
            if (errors.response) {
                errors.response.status === 403 && setError("No se puede iniciar sesión con esas credenciales");
            }
            console.log(errors.response)
            setSendingData(false);
        }
    }

    return (
        <Container>
            <Row>
                <Col lg="5" md="10" sm="10" className="mx-auto">
                    <Card className="mt-5">
                        <Card.Body>

                            <h4>Iniciar Sesión</h4><hr />

                            <Form onSubmit={login}>

                                <Form.Group className="mb-3" controlId="email">
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control
                                        type="email"
                                        value={email}
                                        onChange={e => setEmail(e.target.value)}>
                                    </Form.Control>

                                </Form.Group>

                                <Form.Group className="mb-3" controlId="password">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control
                                        type="password"
                                        value={password}
                                        onChange={e => setPassword(e.target.value)}>
                                    </Form.Control>

                                </Form.Group>

                                <Button type="submit">

                                    {sendingData ? <>
                                        <Spinner
                                            animation="border"
                                            as="span"
                                            size="sm"
                                            role="status"
                                            aria-hidden="true"
                                        ></Spinner>&nbsp;
                                        <span > Iniciando Sesión...</span>
                                    </> : <>
                                        Iniciar Sesión
                                    </>}
                                </Button>

                            </Form>

                            <Alert show={!!error} variant="danger" className="mt-4">{error}</Alert>

                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )

}

export default Login;