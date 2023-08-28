import {useParams} from "react-router-dom";
import {useState} from "react";
import ajax from "../../Services/FetchService";

const ForgottenPasswordNewPassword = () => {
    const {verificationCode} = useParams();
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const baseUrl = "http://localhost:8080/"

    function setNewPassword() {
        const requestBody = {
            verificationCode: verificationCode,
            password: password,
            confirmPassword: confirmPassword
        }
        ajax(`${baseUrl}api/users/register/forgotten-password/new-password`, "PATCH",
            null, requestBody)
            .then()
    }

    return(
        <main className="forgotten-password-send">
            <h1>Change Password</h1>
            <label
                htmlFor="password"
            >
                New Password
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
                Confirm New Password
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
        </main>
    )
}

export default ForgottenPasswordNewPassword;