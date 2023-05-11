import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const ProductPies = () => {
    const user = useUser()
    const [pies, setPies] = useState(null);

    useEffect(() => {
        ajax("/api.products/pies", "GET", user.jwt)
            .then(piesData => {
                setPies(piesData)
            })
    }, [user.jwt])


    return (
        <main></main>
    )
}

export default ProductPies;