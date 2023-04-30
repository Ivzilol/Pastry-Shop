import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useRef, useState} from "react";
import ajax from "../../Services/FetchService";

const AdminUsers = () => {
    const user = useUser();
    const [users, setUsers] = useState("");

    useEffect(() => {
        ajax("/api/users/admin", "GET", user.jwt)
            .then(userData => {
                setUsers(userData);
            })

    }, [user.jwt])

    return (
        <section className="admin-users-container">
            <h3 className="admin-users-container-title">List Users</h3>
            <hr/>
            <div className="admin-users-container-header">
                <table className="admin-users-table">
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Actions</th>
                    </tr>
                    {users ? (
                        <>
                            {users.map(user => (
                                <tr>
                                    <td>{user.id}</td>
                                    <td>{user.username}</td>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    <td>{user.email}</td>
                                    <td>{user.address}</td>
                                    <td className="admin-users-table-buttons">
                                        <button className="delete-button">Delete</button>
                                        <button className="promote-button">Promote</button>
                                    </td>
                                </tr>
                            ))}
                        </>
                    ) : (
                        <></>
                    )}
                </table>
            </div>
        </section>
    )
}

export default AdminUsers;