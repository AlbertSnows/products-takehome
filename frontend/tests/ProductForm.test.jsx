import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { vi } from 'vitest'
import ProductForm from '../src/ProductForm.jsx'
import { createProduct } from '../src/api.js'

vi.mock('../src/api.js')

test('submits the typed name and clears the input', async () => {
    createProduct.mockResolvedValue({ id: '1', name: 'foo' });
    render(<ProductForm />);
    const input = screen.getByRole('textbox');
    await userEvent.type(input, 'foo');
    await userEvent.click(screen.getByRole('button', { name: 'Add' }));
    expect(await screen.findByText('Created')).toBeInTheDocument();
    expect(createProduct).toHaveBeenCalledWith('foo');
    expect(input).toHaveValue('');

})

test('shows an error when the create fails', async () => {
    createProduct.mockRejectedValue(new Error('Request failed: 500'));
    render(<ProductForm />);
    const input = screen.getByRole('textbox');
    await userEvent.type(input, 'aaa');
    await userEvent.click(screen.getByRole('button', { name: 'Add' }));
    expect(await screen.findByText('Request failed: 500')).toBeInTheDocument();

})
