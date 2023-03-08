import {useUser} from "../../UserProvider/UserProvider";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const EditProductsAdmin = () => {

    const user = useUser();
    const {productId} = useParams();

    const [product, setProduct] = useState({
        nameProduct: "",
        price: null,
        categories: "",
        description: "",
        image: "",
        shopName: ""
    })

    useEffect(() => {
        ajax(`/api/products/${productId}`, "GET", user.jwt)
            .then(productResponse => {


            })
    })

    return (
        <div></div>
    );
}

export default EditProductsAdmin;