import {useNavigate, useParams} from "react-router-dom";
import React, {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";

const ForgottenPasswordNewPassword = () => {
    const {verificationCode} = useParams();
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [dialogVisible, setDialogVisible] = useState(false);
    const [dialogErrorWrongFilling, setDialogErrorWrongFilling] = useState(false);
    const [errorResponse, setErrorResponse] = useState("");
    const [errorWrongFilling, setErrorWrongFilling] = useState("");
    const navigate = useNavigate();
    const {t} = useTranslation();

    function setNewPassword() {
        const requestBody = {
            verificationCode: verificationCode,
            password: password,
            confirmPassword: confirmPassword
        }
        if (password.trim() === '' || confirmPassword.trim() === '') {
            setErrorWrongFilling("Please fill in both fields")
            setDialogErrorWrongFilling(true);
            return;
        }
        ajax(`${baseURL}api/users/register/forgotten-password/new-password`, "PATCH",
            null, requestBody)
            .then(response => {
                if (response === undefined) {
                    alert("Successful change your password");
                    navigate("/login")
                } else {
                    handleSubmit()
                    setErrorResponse(response.custom)
                    setDialogVisible(true)
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

    function closeDialog() {
        setDialogVisible(false)
        setDialogErrorWrongFilling(false)
    }

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
                    onFocus={closeDialog}
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
                    onFocus={closeDialog}
                />
                <a className="forgotten-password-send-show-password"
                   onClick={togglePasswordVisibility}>
                    {showPassword ? t('login.hide-password') : t('login.show-password')}
                </a>
                {dialogErrorWrongFilling &&
                    <h5 className="forgotten-password-invalid">{errorWrongFilling}
                    </h5>
                }
                {dialogVisible &&
                    <h5 className="forgotten-password-invalid">{errorResponse}
                    </h5>
                }
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
