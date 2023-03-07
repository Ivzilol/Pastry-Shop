import {useUser} from "../../UserProvider/UserProvider";
import {useState} from "react";

const AdminProducts = () => {
    const user = useUser();
    const [price, setPrice] = useState("");
    const [categories, setCategories] = useState("");
    const [description, setDescription] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [shopId, setShopId] = useState("");

    function createProduct() {
        const requestBody = {
            price: price,
            categories: categories,
            description: description,
            imageUrl: imageUrl,
            shopId: shopId,
        }
        fetch("api/products/admin", {
            headers: {
                "Content-Type": "application/json",
            },
            method: "POST",
            body: JSON.stringify(requestBody),
        })
            .then((response) => {
                if (response.status === 200)
                    return Promise.all([response.json(), response.headers]);
                else return Promise.reject("Invalid product")
            })
            .catch((message) => {
                alert(message)
            });
    }


    return (
        <div></div>
    );
}

export default AdminProducts;
