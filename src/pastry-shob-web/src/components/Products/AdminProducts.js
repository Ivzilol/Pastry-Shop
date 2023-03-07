import {useUser} from "../../UserProvider/UserProvider";
import {useState} from "react";
import NavBar from "../NavBar/NavBar";

const AdminProducts = () => {
    const user = useUser();
    const [price, setPrice] = useState(0);
    const [categories, setCategories] = useState("");
    const [description, setDescription] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [shopId, setShopId] = useState(0);

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
                console.log(requestBody);
                if (response.status === 200)
                    return Promise.all([response.json(), response.headers]);
                else return Promise.reject("Invalid product")
            })
            .catch((message) => {
                alert(message)
            });
    }


    return (
        <>
            <NavBar/>
            <section className="products">
                <article className={"products-form"}>
                    <h1>Add Product</h1>
                    <label
                    htmlFor="price"
                    > Price
                    </label>
                    <input
                        type="number"
                        id="price"
                        autoComplete="off"
                        name="price"
                        placeholder="Price"
                        value={price}
                        onChange={(e) => setPrice(e.target.valueAsNumber)}
                    />
                    <label form="categories">Categories</label>
                    <input
                        type="text"
                        id="categories"
                        name="categories"
                        placeholder="Categories"
                        value={categories}
                        onChange={(e) => e.target.value}
                    />
                    <label className="description">Description</label>
                    <input
                        type="text"
                        id="description"
                        name="description"
                        placeholder="Description"
                        value={description}
                        onChange={(e) => e.target.value}
                    />
                    <label className="imageUrl">Image</label>
                    <input
                    type="text"
                    id="imageUrl"
                    name="imageUrl"
                    placeholder="Image"
                    value={imageUrl}
                    onChange={(e) => e.target.value}
                    />
                    <label className="ShopId">Shop Id</label>
                    <input
                    type="number"
                    id="ShopId"
                    name="ShopId"
                    placeholder="Shop ID"
                    value={shopId}
                    onChange={(e) => e.target.valueAsNumber}
                    />
                </article>
            </section>
        </>
    );
}

export default AdminProducts;
