import {useUser} from "../../UserProvider/UserProvider";
import {useState} from "react";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import {useNavigate} from "react-router-dom";

const CreateProductAdmin = () => {
    useUser();
    const navigate = useNavigate();
    const [name, setName] = useState("");
    const [price, setPrice] = useState(0);
    const [categories, setCategories] = useState("");
    const [description, setDescription] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [shopName, setShopName] = useState("");

    function createProduct() {
        const requestBody = {
            name: name,
            price: price,
            categories: categories,
            description: description,
            imageUrl: imageUrl,
            shopName: shopName,
        }
        fetch("/api/products/create/admin", {
            headers: {
                "Content-Type": "application/json",
            },
            method: "POST",
            body: JSON.stringify(requestBody),
        })
            .then((response) => {
                if (response.status === 200)
                    return Promise.all([response.json(), response.headers]);
                else return Promise.reject("Invalid product");
            })
            .catch((message) => {
                alert(message)
            });
    }


    return (
        <>
            <NavBarAdmin/>
            <section className="products">
                <article className="products-form">
                    <h1>Add Product</h1>
                    <label form="name">Name Product</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        placeholder="Name Product"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
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
                        onChange={(e) => setCategories(e.target.value)}
                    />
                    <label className="description">Description</label>
                    <input
                        type="text"
                        id="description"
                        name="description"
                        placeholder="Description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <label className="imageUrl">Image</label>
                    <input
                    type="text"
                    id="imageUrl"
                    name="imageUrl"
                    placeholder="Image"
                    value={imageUrl}
                    onChange={(e) => setImageUrl(e.target.value)}
                    />
                    <label className="shopName">Shop Name</label>
                    <input
                    type="text"
                    id="ShopId"
                    name="ShopId"
                    placeholder="Shop Name"
                    value={shopName}
                    onChange={(e) => setShopName(e.target.value)}
                    />
                    <button
                        id="submit-product"
                        type="button"
                        onClick={() => createProduct()}
                    >
                    Create Product
                    </button>
                </article>
            </section>
        </>
    );
}

export default CreateProductAdmin;
