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
            {currentUser ? (
                <section className="user-profile-container">
                    <h6>{currentUser.username}</h6>
                    <p>{currentUser.firstName}</p>
                    <p>{currentUser.lastName}</p>
                    <p>{currentUser.email}</p>
                </section>
            ) : (
                <></>
            )}
        </main>
    )
}

export default UserProfile