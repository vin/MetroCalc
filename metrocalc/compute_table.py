#!/usr/bin/python

def bonus(cents):
  return (cents * 107 + 50) / 100

def compute_suggestion(cents, threshold=0):
  result = 1000
  while bonus(result) % 5 or (cents + bonus(result)) % 225 > threshold:
    result += 5
  return result

if __name__ == '__main__':
  print [compute_suggestion(i) for i in range(0, 225, 5)]
