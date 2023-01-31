import {Link, useLocation, useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import jwt_decode from "jwt-decode"
import {Button} from "react-bootstrap";

function NavBar() {
    const navigate = useNavigate();
    const {pathname} = useLocation();
    const user = useUser();
    const [authorities, setAuthorities] = useState(null);

    useEffect(() => {
        if (user && user.jwt) {
            const decodeJwt = jwt_decode(user.jwt);
            setAuthorities(decodeJwt.authorities);
        }
    }, [user, user.jwt])

    return (
        <div className="NavBar nav d-flex justify-content-around justify-content-lg-between">
            <div className="ms-md-5">
                <Link to="/">
                    Pastry Shop Home Page
                </Link>
            </div>
            <div>
                {user && user.jwt ? (
                    <span
                        className="link"
                        onClick={() => {
                            // TODO: have this delete cookie on server side
                            fetch("/api/auth/logout").then((response) => {
                                if (response.status === 200) {
                                    user.setJwt(null);
                                    navigate("/");
                                }
                            });
                        }}
                    >
            Logout
          </span>
                ) : pathname !== "/login" ? (
                    <Button
                        variant="primary"
                        className="me-5"
                        onClick={() => {
                            navigate("/login");
                        }}
                    >
                        Login
                    </Button>
                ) : (
                    <></>
                )}
            </div>
        </div>
    );
}

export default NavBar;