import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";

const UserProfile = () => {
    const user = useUser();
    const [currentUser, setCurrentUser] = useState(null);
    let navigate = useNavigate();
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/users`, "GET", user.jwt)
            .then(userData => {
                setCurrentUser(userData);
            })
            if (!user.jwt) navigate("/login");
    }, [navigate, user.jwt]);


    return (
        <main className="user-profile">
            <NavBar/>
            <h3 className="user-profile-title">Your personal info</h3>
            {currentUser ? (
                <section
                    id={currentUser.id}
                    className="user-profile-container-items">
                    <h6>Username: {currentUser.username}</h6>
                    <p>First Name: {currentUser.firstName}</p>
                    <p>Last Name: {currentUser.lastName}</p>
                    <p>Email: {currentUser.email}</p>
                    <p>Address: {currentUser.address}</p>
                    <button
                    onClick={() => {
                        window.location.href = `/users/${currentUser.id}`;
                    }}
                    >Edit profile</button>
                </section>
            ) : (
                <></>
            )}
        </main>
    )
}

export default UserProfile