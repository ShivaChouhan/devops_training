import pytest
from calculator.calculator import Calculator

@pytest.fixture
def calc():
    return Calculator()

def test_calculator_add(calc):
    assert calc.add(2, 3) == 5

def test_calculator_subtract(calc):
    assert calc.subtract(5, 2) == 3

def test_calculator_memory(calc):
    assert calc.memory_add(5) == 5
    assert calc.memory_add(3) == 8
    assert calc.memory_reset() == 0
