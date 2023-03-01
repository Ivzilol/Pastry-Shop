import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

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
}

export default Register;