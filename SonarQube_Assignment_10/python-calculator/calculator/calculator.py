from .operations import *

class Calculator:
    def __init__(self):
        self._memory = 0

    def add(self, a, b):
        return add(a, b)

    def subtract(self, a, b):
        return subtract(a, b)

    def multiply(self, a, b):
        return multiply(a, b)

    def divide(self, a, b):
        return divide(a, b)

    def memory_add(self, value):
        self._memory += value
        return self._memory

    def memory_reset(self):
        self._memory = 0
        return self._memory
