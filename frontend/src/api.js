const BASE_URL = "http://localhost:8080";

export async function fetchProducts() {
    const response = await fetch(`${BASE_URL}/products`);
    if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`);
    }
    const data = await response.json();
    return data;
}

export async function createProduct(name) {
    const response = await fetch(`${BASE_URL}/products`, {
        method: "POST",
        headers: { "Content-Type": "application/json"},
        body: JSON.stringify({ name }),
    });
    if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`);
    }
    const data = await response.json();
    return data;

}
