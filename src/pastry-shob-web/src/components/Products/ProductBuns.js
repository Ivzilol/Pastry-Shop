import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";

const ProductBuns = () => {
    const user = useUser()
    const [buns, setBuns] = useState(null);
    let navigate = useNavigate();
    useEffect(() => {
        ajax("/api/products/buns", "GET", user.jwt)
            .then(piesData => {
                setBuns(piesData)
            })
        if (!user.jwt) navigate("/login")
    }, [user.jwt])


    return (
        <main>
            <NavBar/>
        </main>
    )
}

export default ProductBuns;