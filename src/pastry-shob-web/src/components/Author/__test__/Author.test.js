import { render, screen } from '@testing-library/react';
import Author from "../Author";

test('find name in title', () => {
    render(<Author />);
    const headingElement = screen.getByText(/Ивайло Иванов Аличков/i);
    expect(headingElement).toBeInTheDocument();
});

test('find name in role', () => {
    render(<Author />);
    const headingElement = screen.getByRole("heading");
    expect(headingElement).toBeInTheDocument();
});