import React, {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";

const ForgottenPasswordEmail = () => {

    const [email, setEmail] = useState("");
    const [dialogVisible, setDialogVisible] = useState(false);
    const [emptyEmailDialog, setEmptyEmailDialog] = useState(false);
    const [error, setError] = useState("")
    const [emptyErrorEmail, setEmptyErrorEmail] = useState("");
    const {t} = useTranslation();

    function sendEmail() {
        if (email.trim() === '') {
            setEmptyErrorEmail("Please put your email");
            setEmptyEmailDialog(true);
            return;
        }
        const requestBody = {
            email: email
        }

        ajax(`${baseURL}api/users/register/forgotten-password`, "POST", null, requestBody)
            .then((response) => {
                if (response === undefined) {
                    alert("Please check your Email");
                    handleSubmit()
                } else {
                    handleSubmit()
                    setError(response.custom)
                    setDialogVisible(true)
                }
            })
    }

    const handleSubmit = () => {
        setEmail("");
    }

    function closeDialog() {
        setDialogVisible(false)
        setEmptyEmailDialog(false)
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
                    onFocus={closeDialog}
                />
                {dialogVisible &&
                    <h5 className="forgotten-password-invalid">{error}
                    </h5>
                }
                {emptyEmailDialog &&
                    <h5 className="forgotten-password-invalid">{emptyErrorEmail}
                    </h5>
                }
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