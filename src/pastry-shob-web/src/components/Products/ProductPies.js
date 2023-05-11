import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";

const ProductPies = () => {
    const user = useUser()
    const [pies, setPies] = useState(null);
    let navigate = useNavigate();
    useEffect(() => {
        ajax("/api/products/pies", "GET", user.jwt)
            .then(piesData => {
                setPies(piesData)
            })
        if (!user.jwt) navigate("/login")
    }, [user.jwt])


    return (
        <main>
            <section className="pies">

            </section>
        </main>
    )
}

export default ProductPies;