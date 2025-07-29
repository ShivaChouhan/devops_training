import pytest
from calculator.operations import add, subtract, multiply, divide, is_even

def test_add():
    assert add(2, 3) == 5
    assert add(-1, 1) == 0

def test_subtract():
    assert subtract(5, 2) == 3
    assert subtract(2, 5) == -3

def test_multiply():
    assert multiply(3, 4) == 12
    assert multiply(-1, 5) == -5

def test_divide():
    assert divide(6, 3) == 2
    assert divide(5, 2) == 2.5
    with pytest.raises(ValueError):
        divide(5, 0)

def test_is_even():
    assert is_even(4) is True
    assert is_even(5) is False
