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
    // find by text looks innside text components
    // to be in the doc confirms it is, in fact, in the doc
    expect(await screen.findByText('Created')).toBeInTheDocument();
    expect(createProduct).toHaveBeenCalledWith('foo');
    expect(input).toHaveValue('');

})

test('shows an error when the create fails', async () => {
    // mock rejected tells the function to fail
    createProduct.mockRejectedValue(new Error('Request failed: 500'));
    render(<ProductForm />);
    const input = screen.getByRole('textbox');
    await userEvent.type(input, 'aaa');
    // finds by role finds the element type
    await userEvent.click(screen.getByRole('button', { name: 'Add' }));
    expect(await screen.findByText('Request failed: 500')).toBeInTheDocument();

})
