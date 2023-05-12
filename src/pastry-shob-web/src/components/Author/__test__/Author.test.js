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

test('render author image', () => {
    render(<Author/>)
    const authorImage = screen.getByAltText("new")
    expect(authorImage).toBeInTheDocument()
});

test('render author bio', () => {
    render(<Author/>)
    const authorBio = screen.getByText(/Здравйте, казвам се Иво и съм автора на приложението на Мама/i)
    expect(authorBio).toBeInTheDocument()
});

test("should render author's contact information", () => {
    render(<Author/>)
    const contactInfo = screen.getByText(/Здравйте, казвам се Иво и съм автора на приложението на Мама/i)
    expect(contactInfo).toBeInTheDocument()
});

