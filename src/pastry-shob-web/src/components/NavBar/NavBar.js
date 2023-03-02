import {Link, useLocation, useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import jwt_decode from "jwt-decode"
import {Button} from "react-bootstrap";

function NavBar() {

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
                <a href="/">Сладкарницата на Мама</a>
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
                    <buton
                        variant="primary"
                        className="nav-button"
                        onClick={() => {
                            navigate("/login");
                        }}
                    >
                        Login
                    </buton>
                ) : (
                    <></>
                )}

                {authorities &&
                authorities.filter((auth) => auth === "ROLE_INSTRUCTOR").length > 0 ? (
                    <Link
                        className="ms-5 ms-md-5 me-md-5 link"
                        to="/instructors/dashboard"
                    >
                        Instructors
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
            </div>
        </section>
    )
}

export default NavBar;