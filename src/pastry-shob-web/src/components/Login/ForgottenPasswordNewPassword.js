import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";

const ForgottenPasswordNewPassword = () => {
    const {verificationCode} = useParams();
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();
    const {t} = useTranslation();
    const baseUrl = "http://localhost:8080/"

    function setNewPassword() {
        const requestBody = {
            verificationCode: verificationCode,
            password: password,
            confirmPassword: confirmPassword
        }
        if (password.trim() === '' || confirmPassword.trim() === '') {
            alert("Please fill in both fields correctly")
            return;
        }
        ajax(`${baseUrl}api/users/register/forgotten-password/new-password`, "PATCH",
            null, requestBody)
            .then(response => {
                console.log(response)
                if (response === undefined) {
                    alert("Successful change your password");
                    navigate("/login")
                } else {
                    handleSubmit()
                    alert(`${response.custom}`)
                }
            })
    }

    const handleSubmit = () => {
        setPassword("");
        setConfirmPassword("");
    }

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <main className="forgotten-password-send">
            <NavBar/>
            <section className="user-forgotten-password-container">
                <h1>{t("user-forgotten-password.h1-pass")}</h1>
                <label
                    htmlFor="password"
                >
                    {t("user-forgotten-password.new-password")}
                </label>
                <input
                    type={showPassword ? "text" : "password"}
                    id="password"
                    name="password"
                    placeholder="New Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <a className="forgotten-password-send-show-password"
                    onClick={togglePasswordVisibility}>
                    {showPassword ? t('login.hide-password') : t('login.show-password')}
                </a>
                <label
                    htmlFor="password"
                >
                    {t("user-forgotten-password.confirm-new-password")}
                </label>
                <input
                    type={showPassword ? "text" : "password"}
                    id="confirmPassword"
                    name="confirmPassword"
                    placeholder="Confirm New Password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                <a className="forgotten-password-send-show-password"
                   onClick={togglePasswordVisibility}>
                    {showPassword ? t('login.hide-password') : t('login.show-password')}
                </a>
                <button
                    id="submit"
                    type="button"
                    onClick={() => setNewPassword()}
                >
                    {t("user-forgotten-password.button-new-password")}
                </button>
            </section>
        </main>
    )
}

export default ForgottenPasswordNewPassword;