import {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";

const ForgottenPasswordEmail = () => {

    const baseUrl = "http://localhost:8080/";
    const [email, setEmail] = useState("");

    function sendEmail() {
        ajax(`${baseUrl}api/users/register/forgotten-password`, "POST", null, email)
            .then(() => {
                handleSubmit()
                alert("Please check your Email");
            })
    }

    const handleSubmit = () => {
        setEmail("");
    }

    return (
        <main className="user-forgotten-password">
            <NavBar/>
            <section className="user-forgotten-password-container">
                <h1>Намиране на вашия акаунт</h1>
                <label htmlFor="email">
                    Напишете своя Имейл
                </label>
                <input
                    type="text"
                    id="email"
                    name="email"
                    placeholder="Enter your Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <button
                    id="submit"
                    type="button"
                    onClick={() => sendEmail()}
                >
                    Send Email
                </button>
            </section>
        </main>
    )
}

export default ForgottenPasswordEmail;