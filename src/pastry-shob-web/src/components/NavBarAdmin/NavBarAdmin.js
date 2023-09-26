import {Link, useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import jwt_decode from "jwt-decode"

function NavBarAdmin() {

    const navigate = useNavigate();
    const {pathname} = useNavigate()
    const user = useUser();
    const [authorities, setAuthorities] = useState(null);

    useEffect(() => {
        if (user && user.jwt) {
            const decodeJwt = jwt_decode(user.jwt);
            setAuthorities(decodeJwt.authorities);
        }
    }, [user, user.jwt]);


    return (
        <section className="nav">
            <article className="nav-home">
                <a href="/">Сладкарницата</a>
            </article>
            <div>
                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            user.setJwt(null);
                            navigate("/");
                        }}
                    >
                        Logout
                    </button>
                ) : pathname !== "/login" ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            navigate("/login");
                        }}
                    >
                        Login
                    </button>
                ) : (
                    <></>
                )}
                {/*Todo: put moderator*/}
                {authorities &&
                authorities.filter((auth) => auth === "ROLE_INSTRUCTOR").length > 0 ? (
                    <Link
                        className="ms-5 ms-md-5 me-md-5 link"
                        to="/instructors/dashboard"
                    >
                        Moderator
                    </Link>
                ) : (
                    <></>
                )}

                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            navigate("/shops");
                        }}
                    >
                        Shops
                    </button>
                ) : (
                    <></>
                )}

                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            navigate("/products");
                        }}
                    >
                       Products
                    </button>
                ) : (
                    <></>
                )}
                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            navigate("/orders");
                        }}
                    >
                        Order
                    </button>
                ) : (
                    <></>
                )}
                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            navigate("/users");
                        }}
                    >
                        Profiles
                    </button>
                ) : (
                    <></>
                )}
                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            navigate("/chat-room");
                        }}
                    >
                        Chats
                    </button>
                ) : (
                    <></>
                )}
            </div>
        </section>
    )
}

export default NavBarAdmin;