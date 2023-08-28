import {useState} from "react";
import ajax from "../../Services/FetchService";

const ForgottenPasswordEmail = () => {

    const baseUrl = "http://localhost:8080/";
    const [email, setEmail] = useState("");

    function sendEmail() {
        const requestBody = {
            email: email
        }
        ajax(`${baseUrl}api/users/change-password`, "POST", null, requestBody)
            .then(() => {
                alert("Please check your Email");
            })
    }


    return (
        <main className="user-forgotten-password">
            <section className="user-forgotten-password-container">
                <h1>Change Password</h1>
                <label htmlFor="email">
                    Please enter your email
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