from fractions import gcd
def solve(t):
	input()
	f = list(map(int, input().split(' ')))

	n = len(f)

	message = [-1] * (n + 1)

	st = -1
	for i in range(n-1):
		p = gcd(f[i], f[i+1])
		if p < f[i] and p > 1:
			st = i
			message[i+1] = p
			break
	assert st >= 0

	e = i
	while e >= 0:
		message[e] = f[e] // message[e+1]
		e -= 1
	e = i+2
	while e < len(message):
		message[e] = f[e-1] // message[e-1]
		e += 1

	els = sorted(set(message))
	sol = ""
	for b in range(len(message)):
		for a in range(len(els)):
			if els[a] == message[b]:
				sol += chr(ord('A') + a)
	print("Case #%d: %s" %(t, sol))

def main():
	T = int(input())
	for t in range(T):
		solve(t+1)

main()