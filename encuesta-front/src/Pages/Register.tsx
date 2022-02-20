import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Card from "react-bootstrap/Card";
import { useState } from "react";
import { registerUser } from "../services/userService";


const Register = () => {

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState<any>({});
    const [sendingData, setSendingData] = useState(false);
    // el error es cualquier tipo de dato y se inicializa como objeto vacio

    const register = async (e: React.SyntheticEvent) => {
        // usamos prevent default ya q se le aplica el evente a un form
        e.preventDefault();

        try {
            // el siguiente es el "register" de userService.ts
            setSendingData(true);
            await registerUser(name, email, password);
            // redireccionar usuario al panel
            setSendingData(false);

        } catch (errors: any) {
            setErrors(errors.response.data.errors); // errores q vienen desde el back-end
            setSendingData(false);

        }

        // console.log("name: " + name + ", email: " + email + ", password: " + password);
    }

    return (
        <Container>
            <Row>
                <Col lg="5" md="10" sm="10" className="mx-auto">
                    <Card className="mt-5">
                        <Card.Body>

                            <h4>Crear Cuenta</h4><hr />

                            <Form onSubmit={register}>

                                <Form.Group className="mb-3" controlId="name">
                                    <Form.Label>Nombre</Form.Label>
                                    <Form.Control
                                        isInvalid={!!errors?.name} //esto pregunta si existe error con "?" y ademas lo castea a un booleano con "!!"
                                        type="text"
                                        value={name}
                                        onChange={e => setName(e.target.value)}>
                                    </Form.Control>
                                    <Form.Control.Feedback type="invalid">
                                        {errors?.name}
                                    </Form.Control.Feedback>
                                </Form.Group>

                                <Form.Group className="mb-3" controlId="email">
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control
                                        isInvalid={!!errors?.email}
                                        type="email"
                                        value={email}
                                        onChange={e => setEmail(e.target.value)}>
                                    </Form.Control>
                                    <Form.Control.Feedback type="invalid">
                                        {errors?.email}
                                    </Form.Control.Feedback>
                                </Form.Group>

                                <Form.Group className="mb-3" controlId="password">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control
                                        isInvalid={!!errors?.password}
                                        type="password"
                                        value={password}
                                        onChange={e => setPassword(e.target.value)}>
                                    </Form.Control>
                                    <Form.Control.Feedback type="invalid">
                                        {errors?.password}
                                    </Form.Control.Feedback>
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
                                        <span > Creando Cuenta...</span>
                                    </> : <>
                                        Crear Cuenta
                                    </>}
                                </Button>

                            </Form>

                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )

}

export default Register;