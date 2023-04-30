import React, {useEffect, useRef, useState} from 'react';
import ajax from "../Services/FetchService";
import {Dropdown, Button, ButtonGroup, Col, Container, DropdownButton, Form, Row} from "react-bootstrap";
import StatusBadge from "../components/StatusBadge/StatusBadge";
import {useNavigate, useParams} from "react-router-dom";
import {useUser} from "../UserProvider/UserProvider";
import Comment from "../components/Comment/Comment";
import NavBar from "../components/NavBar/NavBar";
import ShopArt from "../components/ShopArt/ShopArt";
import Footer from "../components/Footer/Footer";

const ShopsView = () => {
    useNavigate();
    const user = useUser();
    const {shopId} = useParams()
    // const shopId = window.location.href.split("/shops/")[1];
    const [shop, setShop] = useState({
        town: "",
        address: "",
        number: null,
        status: ""
    });

    const [shopsEnums, setShopsEnums] = useState([]);
    const [shopsStatuses, setShopsStatuses] = useState([]);
    const prevShopValue = useRef(shop);

    const emptyComment = {
        id: null,
        text: "",
        shopId: shopId !== null ? parseInt(shopId) : null,
        user: user.jwt,
    }

    const [comment, setComment] = useState(emptyComment);
    const [comments, setComments] = useState([]);


    function handleEditComment(commentId) {
        const index = comments.findIndex(comment => comment.id === commentId);
        const commentCopy = {
            id: comments[index].id,
            text: comments[index].text,
            shopId: shopId !== null ? parseInt(shopId) : null,
            user: user.jwt,
        }
        setComment(commentCopy);
    }

    function refreshPage() {
        window.location.reload();
    }

    function handleDeleteComment(commentId) {
        ajax(`/api/comments/user/${commentId}`, "DELETE", user.jwt)
            .then(() => {
                refreshPage();
            });
    }

    function submitComment() {
        if (comment.id) {
            ajax(`/api/comments/${comment.id}`, "PUT", user.jwt, comment).then(d => {
                const commentsCopy = [...comments]
                const index = commentsCopy.findIndex(comment => comment.id === d.id);
                commentsCopy[index] = d;
                setComments(commentsCopy);
                setComment(emptyComment);
            })
        } else {
            ajax('/api/comments', 'POST', user.jwt, comment).then(d => {
                const commentsCopy = [...comments]
                commentsCopy.push(d);
                setComments(commentsCopy);
                setComment(emptyComment);
            });
        }
    }

    useEffect(() => {
        ajax(`/api/comments?shopId=${shopId}`,
            'GET',
            user.jwt,
            null)
            .then(commentsData => {
                setComments(commentsData)
            });
    }, [])

    function updateComment(value) {
        const commentCopy = {...comment}
        commentCopy.text = value;
        setComment(commentCopy);
    }

    function updateShop(prop, value) {
        const newShop = {...shop}
        newShop[prop] = value;
        setShop(newShop);
    }

    function saveShop() {
        if (shop.status === shopsStatuses[0].status) {
            updateShop("status", shopsStatuses[1].status)
        } else {
            persist();
        }
    }

    function persist() {
        ajax(`/api/shops/${shopId}`, "PUT", user.jwt, shop)
            .then(shopData => {
                    setShop(shopData);
                }
            );
    }

    useEffect(() => {
        if (prevShopValue.current.status !== shop.status) {
            persist();
        }
        prevShopValue.current = shop;
    }, [shop])

    useEffect(() => {
        ajax(`/api/shops/${shopId}`, "GET", user.jwt)
            .then(shopResponse => {
                let shopData = shopResponse.shops;
                if (shopData.town === null) shopData.town = ""
                if (shopData.address === null) shopData.address = ""
                setShop(shopData);
                setShopsEnums(shopResponse.shopsEnums);
                setShopsStatuses(shopResponse.statusEnums);
            });
    }, []);

    return (
        <main className="user-comments-container">
            <NavBar/>
            <ShopArt/>
            <div className="comments">
                        <textarea placeholder="Напиешете Вашето мнение тук..."
                            className="comments-textarea"
                            onChange={(e) => updateComment(e.target.value)}
                            value={comment.text}
                        >
                        </textarea>
                <button className="user-comments-container-button"
                    onClick={() => submitComment()}
                >Post Comment
                </button>
            </div>
            <div className="comments-view">
                {comments.map(currentComment => (
                    <Comment
                        createdBy={currentComment.createdBy}
                        text={currentComment.text}
                        emitDeleteComment={handleDeleteComment}
                        emitEditComment={handleEditComment}
                        id={currentComment.id}
                    />
                ))}
            </div>
            <Footer/>
        </main>
    );
};

export default ShopsView;