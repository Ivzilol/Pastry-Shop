import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";

const UserProfile = () => {
    const user = useUser();
    const [currentUser, setCurrentUser] = useState(null);
    let navigate = useNavigate();

    useEffect(() => {
        ajax("/api/users", "GET", user.jwt)
            .then(userData => {
                setCurrentUser(userData);
            })
            if (!user.jwt) navigate("/login");
    }, [user.jwt]);


    return (
        <main className="user-profile">
            <h3 className="user-profile">Your personal info</h3>
            {currentUser ? (
                <section
                    id={currentUser.id}
                    className="user-profile-container">
                    <h6>Username: {currentUser.username}</h6>
                    <p>First Name: {currentUser.firstName}</p>
                    <p>Last Name: {currentUser.lastName}</p>
                    <p>Email: {currentUser.email}</p>
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