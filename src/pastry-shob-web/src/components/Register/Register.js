import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInfoCircle, faCheck, faTimes} from "@fortawesome/free-solid-svg-icons";

const USER_REGEX = /^[a-zA-z][a-zA-z0-9-_]{3.23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8.24}$/;

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

    const userRef = useRef();
    const errRef = useRef();

    const [userReg, setUserReg] = useState('');
    const [validName, setValidName] = useState(false);
    const [userFocus, setUserFocus] = useState(false);

    const [pwd, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);


    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        const result = USER_REGEX.test(userReg);
        console.log(result);
        console.log(userReg);
        setValidName(result);
    }, [userReg]);

    useEffect(() => {
        const result = PWD_REGEX.test(pwd);
        console.log(result);
        console.log(pwd);
        console.log(result);
        setPwd(pwd);
    }, [pwd]);

    useEffect(() => {
        setErrMsg('');

    }, [userReg, pwd]);


    return (
        <section className="register">
            <article className="register-form">
                {/*<p ref={errRef} className={errMsg ? "errmsg" :*/}
                {/*    "offscreen"} aria-live="assertive">{errMsg}</p>*/}
                <h1>Register</h1>
                <label
                    htmlFor="username"
                >Username
                    {/*<span className={validName ? "is-valid" : "visually-hidden"}>*/}
                    {/*    <FontAwesomeIcon icon={faCheck}/>*/}
                    {/*</span>*/}
                    {/*<span className={validName || !userReg ? "is-valid" : "visually-hidden"}>*/}
                    {/*    <FontAwesomeIcon icon={faTimes}/>*/}
                    {/*</span>*/}
                </label>
                <input
                    type="text"
                    id="username"
                    ref={userRef}
                    autoComplete="off"
                    name="username"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                    aria-invalid={validName ? "false" : "true"}
                    aria-describedby="uidnote"
                    onFocus={() => setUserFocus(true)}
                    onBlur={() => setUserFocus(false)}
                />
                {/*<p id="uidnote" className={userFocus && userReg &&*/}
                {/*!validName ? "instructions" : "offscreen"}>*/}
                {/*    <FontAwesomeIcon icon={faInfoCircle}/>*/}
                {/*    3 to 20 characters.<br/>*/}
                {/*    Must Begin with a letter.<br/>*/}
                {/*</p>*/}
                <label form="password">Password</label>
                <input
                    type="text"
                    id="password"
                    name="password"
                    placeholder="Password"
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