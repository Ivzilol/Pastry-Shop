import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Button} from "react-bootstrap";

const Register = () => {

    const user = useUser();
    const navigate = useNavigate()
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");

    useEffect(() => {
        if (user.jwt) navigate("/shops");
    }, [user]);

    function createAndLoginUser() {
        const requestBody = {
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            email: email,
            address: address,
        }
        console.log(requestBody);
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
            })
            .catch((message) => {
                alert(message);
            });
    }

    return(
        <section className="register">
            <article className="register-form">
                <label form="username">Username</label>
                <input
                type="text"
                id="username"
                name="username"
                placeholder="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                />
                <label form="password">Password</label>
                <input
                    type="text"
                    id="password"
                    name="password"
                    placeholder="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <label form="firstName">First Name</label>
                <input
                    type="text"
                    id="firstName"
                    name="firstName"
                    placeholder="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                />
                <label form="lastName">Last Name</label>
                <input
                    type="text"
                    id="lastName"
                    name="lastName"
                    placeholder="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                />
                <label form="email">Email</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <label form="address">Address</label>
                <input
                    type="text"
                    id="address"
                    name="address"
                    placeholder="Address"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                />
                <button
                id="submit"
                type="button"
                onClick={() => createAndLoginUser()}
                >
                    Register
                </button>
            </article>
        </section>
    )
}

export default Register;