import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";

const ForgottenPasswordNewPassword = () => {
    const {verificationCode} = useParams();
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const navigate = useNavigate();
    const baseUrl = "http://localhost:8080/"

    function setNewPassword() {
        const requestBody = {
            verificationCode: verificationCode,
            password: password,
            confirmPassword: confirmPassword
        }
        ajax(`${baseUrl}api/users/register/forgotten-password/new-password`, "PATCH",
            null, requestBody)
            .then(response => {
                if (response !== undefined) {
                    alert("Successful changed your password")
                    navigate("/login");
                } else {
                    alert("Error")
                }
            })
    }

    return (
        <main className="forgotten-password-send">
            <NavBar/>
            <section className="user-forgotten-password-container">
                <h1>Смяна на паролата</h1>
                <label
                    htmlFor="password"
                >
                    Нова парола
                </label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="New Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <label
                    htmlFor="password"
                >
                    Потвърди новата парола
                </label>
                <input
                    type="password"
                    id="confirmPassword"
                    name="confirmPassword"
                    placeholder="Confirm New Password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                <button
                    id="submit"
                    type="button"
                    onClick={() => setNewPassword()}
                >
                    Change Password
                </button>
            </section>
        </main>
    )
}

export default ForgottenPasswordNewPassword;