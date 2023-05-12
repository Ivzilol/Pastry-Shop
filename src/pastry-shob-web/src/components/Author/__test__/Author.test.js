import { render, screen } from '@testing-library/react';
import Author from "../Author";

test('find name in title', () => {
    render(<Author />);
    const headingElement = screen.getByText(/Ивайло Иванов Аличков/i);
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

test('find name in role', () => {
    render(<Author />);
    const headingElement = screen.getByRole("heading");
    expect(headingElement).toBeInTheDocument();
});

test("find name in role 2", () => {
    render(<Author/>)
    const paragraphElement = screen.getByText("Поздарви")
    expect(paragraphElement).toBeInTheDocument()
})

test("find Title", () => {
    render(<Author/>)
    const titleElement = screen.getByTestId("Author");
    expect(titleElement).toBeInTheDocument();
})

// Find By

test('find name in title async', async () => {
    render(<Author />);
    const headingElement = await screen.findByText(/Ивайло Иванов Аличков/i);
    expect(headingElement).toBeInTheDocument();
});

// QueryBy

test('find name in title query', async () => {
    render(<Author />);
    const headingElement = screen.queryByText(/Zahari/i);
    expect(headingElement).not.toBeInTheDocument();
});

test('find name in title all', async () => {
    render(<Author />);
    const headingElement = screen.getAllByRole("heading");
    expect(headingElement.length).toBe(1);
});