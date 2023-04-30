import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const AdminUsers = () => {
    const user = useUser();
    const [users, setUsers] = useState(null);

    useEffect(() => {
        ajax("/api/users/admin", "GET", user.jwt)
            .then(userData => {
                setUsers(userData);
            })

    }, [user.jwt])

    return (
        <main className="admin-users-container">
            <div className="admin-users-container-header">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div className="admin-users-container-content">
                {users.map(user => (
                    <tbody className="admin-users-container-content-tbody"
                    id={user.id}
                    >
                        <tr>
                            <td>{user.id}</td>
                            <td>{user.username}</td>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>{user.email}</td>
                            <td>{user.address}</td>
                        </tr>
                    </tbody>
                ))}
            </div>
        </main>
    )
}

export default AdminUsers;