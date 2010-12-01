#!/usr/bin/python

def bonus(cents):
  return (cents * 115 + 50) / 100

def compute_suggestion(cents):
  result = 800
  while bonus(result) % 5 or round(cents + bonus(result)) % 225:
    result += 5
  return result

print [compute_suggestion(i) for i in range(0, 225, 5)]
