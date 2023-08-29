import {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";

const ForgottenPasswordEmail = () => {

    const baseUrl = "http://localhost:8080/";
    const [email, setEmail] = useState("");
    const {t} = useTranslation();

    function sendEmail() {
        if (email.trim() === '') {
            alert("Please put your email")
            return;
        }
        const requestBody = {
            email: email
        }

        ajax(`${baseUrl}api/users/register/forgotten-password`, "POST", null, requestBody)
            .then((response) => {
                if (response === undefined) {
                    alert("Please check your Email");
                } else {
                    let message = response.custom
                    handleSubmit()
                    alert(`${message}`)
                }
            })
    }

    const handleSubmit = () => {
        setEmail("");
    }

    return (
        <main className="user-forgotten-password">
            <NavBar/>
            <section className="user-forgotten-password-container">
                <h1>{t('user-forgotten-password.h1')}</h1>
                <label htmlFor="email">
                    {t('user-forgotten-password.label')}
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
                    {t('user-forgotten-password.button')}
                </button>
            </section>
        </main>
    )
}

export default ForgottenPasswordEmail;