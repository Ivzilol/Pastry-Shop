import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";
import NavBar from "../NavBar/NavBar";


const Register = () => {

    const user = useUser();
    const navigate = useNavigate()
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("")
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");

    useEffect(() => {
        if (user.jwt) navigate("/");
    }, [user]);

    function createAndLoginUser() {
        const requestBody = {
            username: username,
            password: password,
            confirmPassword: confirmPassword,
            firstName: firstName,
            lastName: lastName,
            email: email,
            address: address,
            phoneNumber: phoneNumber
        }

        fetch("api/users/register", {
            headers: {
                "Content-Type": "application/json",
            },
            method: "POST",
                    body: JSON.stringify(requestBody),
        })
            .then((response) => {
                if (response.status === 200)
                    return Promise.all([response.json(), response.headers]);
                else return Promise.reject("Invalid attempt");
            })
            .then(([body, headers]) => {
                user.setJwt(user.jwt);
                navigate("/login")
            })
            .catch((message) => {
                alert(message);
            });
    }

    const [error, setError] = useState({
        username: '',
        password: '',
        confirmPassword: "",
        email: '',
        firstName: '',
        lastName: '',
        address: '',
        phoneNumber: ''
    })

    const validateUsername = (e) => {
        const username = e.target.value;
        let errorMessage = ''
        if (username.length < 3) {
            errorMessage = 'Username must be longer form 3 characters';
        } else if (username.length > 20) {
            errorMessage = 'Username must be in short from 20 characters';
        }
        setError(state => ({
            ...state,
            username: errorMessage,
        }));
    }

    const validatePassword = (e) => {
        const password = e.target.value;
        let errorMessage = ''
        if (password.length < 3) {
            errorMessage = 'Password must be longer form 3 characters';
        } else if (password.length > 20) {
            errorMessage = 'Password must be in short from 20 characters';
        }
        setError(state => ({
            ...state,
            password: errorMessage,
        }));
    }

    const validateConfirmPassword = (e) => {
        const confirmPassword = e.target.value;
        let errorMessage = ''
        if (confirmPassword.length < 3) {
            errorMessage = 'Password must be longer form 3 characters';
        } else if (confirmPassword.length > 20) {
            errorMessage = 'Password must be in short from 20 characters';
        }
        setError(state => ({
            ...state,
            confirmPassword: errorMessage,
        }));
    }

    const validateFirstName = (e) => {
        const firstName = e.target.value;
        let errorMessage = ''
        if (firstName.length < 1) {
            errorMessage = 'First Name it can not be empty';
        }
        setError(state => ({
            ...state,
            firstName: errorMessage,
        }));
    }

    const validateLastName = (e) => {
        const lastName = e.target.value;
        let errorMessage = ''
        if (lastName.length < 1) {
            errorMessage = 'Last Name it can not be empty';
        }
        setError(state => ({
            ...state,
            lastName: errorMessage,
        }));
    }

    const validateEmail = (e) => {
        const email = e.target.value;
        let errorMessage = ''
        if (email.length < 1) {
            errorMessage = 'Email it can not be empty';
        }
        setError(state => ({
            ...state,
            email: errorMessage,
        }));
    }

    const validateAddress = (e) => {
        const address = e.target.value;
        let errorMessage = ''
        if (address.length < 1) {
            errorMessage = 'Address it can not be empty';
        }
        setError(state => ({
            ...state,
            address: errorMessage,
        }));
    }

    const validatePhoneNumber = (e) => {
        const phoneNumber = e.target.value;
        let errorMessage = ''
        if (phoneNumber.length < 1) {
            errorMessage = 'Phone number it can not be empty';
        }
        setError(state => ({
            ...state,
            phoneNumber: errorMessage,
        }));
    }


    return (
        <>
            <NavBar/>
            <section className="register">
                <article className="register-form">
                    <h1>Register</h1>
                    <label
                        htmlFor="username"
                    >Username
                    </label>
                    <input
                        type="text"
                        id="username"
                        autoComplete="off"
                        name="username"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        onBlur={validateUsername}
                    />
                    {error.username &&
                        <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {error.username}</span>
                    }

                    <label form="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        onBlur={validatePassword}
                    />
                    {error.password &&
                        <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.password}</span>
                    }

                    <label form="confirmPassword">Confirm Password</label>
                    <input
                        type="password"
                        id="confirmPassword"
                        name="confirmPassword"
                        placeholder="ConfirmPassword"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        onBlur={validateConfirmPassword}
                    />
                    {error.confirmPassword &&
                        <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.confirmPassword}</span>
                    }
                    <label form="firstName">First Name</label>
                    <input
                        type="text"
                        id="firstName"
                        name="firstName"
                        placeholder="First Name"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        onBlur={validateFirstName}
                    />
                    {error.firstName &&
                        <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.firstName}</span>
                    }
                    <label form="lastName">Last Name</label>
                    <input
                        type="text"
                        id="lastName"
                        name="lastName"
                        placeholder="Last Name"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        onBlur={validateLastName}
                    />
                    {error.lastName &&
                        <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.lastName}</span>
                    }
                    <label form="email">Email</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        onBlur={validateEmail}
                    />
                    {error.email &&
                        <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.email}</span>
                    }
                    <label form="address">Address</label>
                    <input
                        type="text"
                        id="address"
                        name="address"
                        placeholder="Address"
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                        onBlur={validateAddress}
                    />
                    {error.address &&
                        <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.address}</span>
                    }
                    <label form="phoneNumber">Phone Number</label>
                    <input
                        type="text"
                        id="phoneNumber"
                        name="phoneNumber"
                        placeholder="PhoneNumber"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        onBlur={validatePhoneNumber}
                    />
                    {error.phoneNumber &&
                        <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.phoneNumber}</span>
                    }
                    <button
                        id="submit"
                        type="button"
                        onClick={() => createAndLoginUser()
                    }
                    >
                        Register
                    </button>
                </article>
            </section>
        </>
    )
}

export default Register;