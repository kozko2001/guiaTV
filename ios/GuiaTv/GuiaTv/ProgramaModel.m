//
//  ProgramaModel.m
//  GuiaTv
//
//  Created by Jordi Coscolla on 24/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ProgramaModel.h"

@implementation ProgramaModel

@synthesize title, categoria, start, end, desc, fecha, imagen;

- (ProgramaModel*) initProgramaWithTitle: (NSString *)t AndDesc: (NSString*) d AndCategoria:(NSString*) cat AndStart: (double) _start AndEnd: (double) _end AndImage: (NSString*) _imagen;
{
    self = [self init];
    NSLog(@"%@ %@" , t, _imagen);
    
    self.title = t;
    self.desc = d;
    self.categoria = cat;
    self.start = _start;
    self.end   = _end;
    self.imagen= _imagen;
    self.fecha = [[NSString alloc] initWithFormat: @"%i", start];
    return self;
}
@end
